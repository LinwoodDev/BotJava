package com.github.codedoctorde.linwood.game.mode.whatisit;

import com.github.codedoctorde.linwood.Main;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import com.github.codedoctorde.linwood.game.Game;
import com.github.codedoctorde.linwood.game.GameMode;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import org.hibernate.Session;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author CodeDoctorDE
 */
public class WhatIsIt implements GameMode {
    private Game game;
    private WhatIsItRound round;
    private long textChannelId;
    private final List<Long> wantWriter = new ArrayList<>();
    private Long wantWriterMessageId;
    private int maxRounds;
    private int currentRound;
    private Random random = new Random();
    private Timer timer = new Timer();
    private WhatIsItEvents events;
    private final HashMap<Long, Integer> points = new HashMap<>();

    @Override
    public void start(Game game) {
        this.game = game;

        events = new WhatIsItEvents(this);
        Main.getInstance().getJda().getEventManager().register(events);
        var session = Main.getInstance().getDatabase().getSessionFactory().openSession();
        var guild = GuildEntity.get(session, game.getGuildId());
        Category category = null;
        if(guild.getGameCategoryId() != null)
        category = guild.getGameCategory();
        var bundle = getBundle(session);
        Category finalCategory = category;
        game.getGuild().createTextChannel(MessageFormat.format(bundle.getString("TextChannel"), game.getId())).queue((textChannel -> {
            this.textChannelId = textChannel.getIdLong();
            if(finalCategory != null)
                textChannel.getManager().setParent(finalCategory).queue();
            chooseNextPlayer(session);
        }));
    }

    @Override
    public void stop() {
        stopTimer();
        Main.getInstance().getJda().getEventManager().unregister(events);
        if(round != null)
            round.stopTimer();
        var textChannel = getTextChannel();
        if(textChannel != null)
            textChannel.delete().queue();
    }

    public void chooseNextPlayer(Session session){
        var bundle = getBundle(session);
        stopTimer();
        getTextChannel().sendMessage(MessageFormat.format(bundle.getString("Next"), currentRound + 1)).queue(message -> {
            wantWriterMessageId = message.getIdLong();
            message.addReaction("\uD83D\uDD90️").queue(aVoid ->
                message.addReaction("⛔").queue());
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    var session = Main.getInstance().getDatabase().getSessionFactory().openSession();
                    stopTimer();
                    if (wantWriter.size() < 1) finishGame();
                    else nextRound(session, wantWriter.get(random.nextInt(wantWriter.size())));
                    session.close();
                }
            }, 30 * 1000);
        });
    }

    public void stopTimer(){
        try{
            timer.cancel();
            timer = new Timer();
        }catch(Exception ignored){

        }
    }
    public void nextRound(Session session, long writerId){
        round = new WhatIsItRound(writerId, this);
        var bundle = getBundle(session);
        getTextChannel().sendMessage(MessageFormat.format(bundle.getString("Round"), round.getWriter().getAsMention())).embed(getTopListEmbed(session)).queue();
        round.inputWriter();
    }
    public void cancelRound(Session session){
        var bundle = getBundle(session);
        getTextChannel().sendMessage(bundle.getString("Cancel")).queue();
        finishRound(session);
    }

    public void finishGame(){
        stopTimer();
        var session = Main.getInstance().getDatabase().getSessionFactory().openSession();
        var bundle = getBundle(session);
        var textChannel = getTextChannel();
        textChannel.sendMessage(bundle.getString("Finish")).embed(getTopListEmbed(session)).queue();
        session.close();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                stopTimer();
                Main.getInstance().getGameManager().stopGame(game);
            }
        }, 10000);
    }

    public void finishRound(Session session){
        round.stopRound();
        round = null;
        wantWriterMessageId = null;

        chooseNextPlayer(session);
    }

    public MessageEmbed getTopListEmbed(Session session){
        var bundle = getBundle(session);
        return new EmbedBuilder().setTitle(bundle.getString("LeaderboardHeader")).setDescription(topListString(session)).setFooter(bundle.getString("LeaderboardFooter")).build();
    }

    private ArrayList<Map.Entry<Long, Integer>> topList() {
        var set = points.entrySet();
        var list = new ArrayList<>(set);
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        return list;
    }
    private String topListString(Session session){
        var topList = topList();
        var builder = new StringBuilder();
        var bundle = getBundle(session);
        for (int i = 0; i < topList.size(); i++) {
            var entry = topList.get(i);
            builder.append(MessageFormat.format(bundle.getString("Leaderboard"), i + 1,
                    Objects.requireNonNull(Main.getInstance().getJda().getUserById(entry.getKey())).getAsMention(), entry.getValue()));
        }
        return builder.toString();
    }

    public ResourceBundle getBundle(Session session){
        return ResourceBundle.getBundle("locale.game.WhatIsIt", Main.getInstance().getDatabase().getGuildById(session, game.getGuildId()).getLocalization());
    }

    public long getTextChannelId() {
        return textChannelId;
    }

    public TextChannel getTextChannel(){
        return Main.getInstance().getJda().getTextChannelById(textChannelId);
    }

    public Game getGame() {
        return game;
    }

    public WhatIsItRound getRound() {
        return round;
    }

    public HashMap<Long, Integer> getPoints() {
        return points;
    }
    public void givePoints(Member member, int number){
        points.put(member.getIdLong(), points.getOrDefault(member.getIdLong(), 0) + number);
    }
    public int getPoints(Member member){
        return points.getOrDefault(member.getIdLong(), 0);
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public int getMaxRounds() {
        return maxRounds;
    }

    public List<Long> getWantWriter() {
        return wantWriter;
    }

    public Long getWantWriterMessageId() {
        return wantWriterMessageId;
    }

    public void wantWriter(Session session, Member member) {
        if(!wantWriter.contains(member.getIdLong())) wantWriter.add(member.getIdLong());
        getTextChannel().sendMessage(MessageFormat.format(getBundle(session).getString("Join"), member.getUser().getAsMention())).queue();
    }

    public void removeWriter(Session session, Member member) {
        wantWriter.remove(member.getIdLong());
        getTextChannel().sendMessage(MessageFormat.format(getBundle(session).getString("Leave"), member.getUser().getAsMention())).queue();
    }
    public void giveWriterPoints(Session session){
        if(round == null)
            return;
        givePoints(round.getWriter(), round.getGuesser().size() * 2);
    }
}
