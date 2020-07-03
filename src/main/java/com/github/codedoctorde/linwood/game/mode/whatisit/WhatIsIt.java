package com.github.codedoctorde.linwood.game.mode.whatisit;

import com.github.codedoctorde.linwood.Linwood;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import com.github.codedoctorde.linwood.game.Game;
import com.github.codedoctorde.linwood.game.GameMode;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;
import org.hibernate.Session;

import java.text.MessageFormat;
import java.util.*;

/**
 * @author CodeDoctorDE
 */
public class WhatIsIt implements GameMode {
    private Game game;
    private WhatIsItRound round;
    private long textChannelId;
    private final HashSet<Long> wantWriter = new HashSet<>();
    private Long wantWriterMessageId;
    private final int maxRounds;
    private int currentRound = 0;
    private final Random random = new Random();
    private Timer timer = new Timer();
    private WhatIsItEvents events;
    private final long rootChannel;
    private final HashMap<Long, Integer> points = new HashMap<>();

    public WhatIsIt(int maxRounds, long rootChannel){
        this.maxRounds = maxRounds;
        this.rootChannel = rootChannel;
    }

    @Override
    public void start(Game game) {
        this.game = game;

        events = new WhatIsItEvents(this);
        Linwood.getInstance().getJda().getEventManager().register(events);
        var session = Linwood.getInstance().getDatabase().getSessionFactory().openSession();
        var guild = GuildEntity.get(session, game.getGuildId());
        Category category = null;
        if(guild.getGameCategoryId() != null)
        category = guild.getGameCategory();
        var bundle = getBundle(session);
        Category finalCategory = category;
        ChannelAction<TextChannel> action;
        if(finalCategory == null)
            action = game.getGuild().createTextChannel(MessageFormat.format(bundle.getString("TextChannel"),game.getId()));
        else
            action = finalCategory.createTextChannel(MessageFormat.format(bundle.getString("TextChannel"),game.getId()));
        action.queue((textChannel -> {
            this.textChannelId = textChannel.getIdLong();
            if(finalCategory != null)
                textChannel.getManager().setParent(finalCategory).queue();
            chooseNextPlayer(session);
        }));
    }

    @Override
    public void stop() {
        stopTimer();
        var session = Linwood.getInstance().getDatabase().getSessionFactory().openSession();
        Linwood.getInstance().getJda().getEventManager().unregister(events);
        if(round != null)
            round.stopTimer();
        sendLeaderboard(session, Linwood.getInstance().getJda().getTextChannelById(rootChannel));
        session.close();
        var textChannel = getTextChannel();
        if(textChannel != null)
            textChannel.delete().queue();
    }

    public void chooseNextPlayer(Session session){
        var bundle = getBundle(session);
        sendLeaderboard(session);
        getTextChannel().sendMessage(MessageFormat.format(bundle.getString("Next"), currentRound + 1)).queue(message -> {
            wantWriterMessageId = message.getIdLong();
            message.addReaction("\uD83D\uDD90️").queue(aVoid ->
                message.addReaction("⛔").queue());
            stopTimer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    stopTimer();
                    var session = Linwood.getInstance().getDatabase().getSessionFactory().openSession();
                    var wantWriterList = new ArrayList<>(wantWriter);
                    if (wantWriter.size() < 1) finishGame();
                    else nextRound(session, wantWriterList.get(random.nextInt(wantWriterList.size())));
                    session.close();
                }
            }, 45 * 1000);
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
        currentRound++;
        round = new WhatIsItRound(writerId, this);
        var bundle = getBundle(session);
        game.getGuild().retrieveMemberById(writerId).queue(member -> {
            var session1 = Linwood.getInstance().getDatabase().getSessionFactory().openSession();
            getTextChannel().sendMessage(MessageFormat.format(bundle.getString("Round"), member.getAsMention())).queue();
            sendLeaderboard(session1);
            session1.close();
            round.inputWriter();
        });
    }
    public void cancelRound(Session session){
        var bundle = getBundle(session);
        getTextChannel().sendMessage(bundle.getString("Cancel")).queue();
        finishRound();
    }

    public void finishGame(){
        stopTimer();
        clearWantWriterMessage();
        var session = Linwood.getInstance().getDatabase().getSessionFactory().openSession();
        var bundle = getBundle(session);
        var textChannel = getTextChannel();
        textChannel.sendMessage(bundle.getString("Finish")).queue();
        sendLeaderboard(session);
        session.close();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                stopTimer();
                Linwood.getInstance().getGameManager().stopGame(game);
            }
        }, 30 * 1000);
    }

    public void clearWantWriterMessage(){
        if(wantWriterMessageId == null)
            return;
        getTextChannel().retrieveMessageById(wantWriterMessageId).queue(message -> message.delete().queue() );
        wantWriterMessageId = null;
    }

    public void finishRound(){
        game.getGuild().retrieveMemberById(round.getWriterId()).queue(member ->{
            var session = Linwood.getInstance().getDatabase().getSessionFactory().openSession();
            givePoints(member, round.getGuesser().size() * 2);
            round.stopRound();
            round = null;
            wantWriterMessageId = null;
            wantWriter.clear();
            if(currentRound >= maxRounds) {
                finishGame();
                return;
            }
            chooseNextPlayer(session);
            session.close();
        });
    }

    public void sendLeaderboard(Session session){
        sendLeaderboard(session, getTextChannel());
    }

    public void sendLeaderboard(Session session, TextChannel textChannel){
        var bundle = getBundle(session);
        sendLeaderboard(0, "", " ", bundle, textChannel);
    }

    private void sendLeaderboard(int index, String description, String message, ResourceBundle bundle){
        sendLeaderboard(index, description, message, bundle, getTextChannel());
    }

    private void sendLeaderboard(int index, String description, String message, ResourceBundle bundle, TextChannel textChannel){
        var leaderboard = getLeaderboard();
        if(textChannel == null)
            return;
        if(index >= leaderboard.size())
            textChannel.sendMessage(message).embed(new EmbedBuilder().setTitle(bundle.getString("LeaderboardHeader")).setDescription(description).setFooter(bundle.getString("LeaderboardFooter")).build()).queue();
        else{
            Linwood.getInstance().getJda().retrieveUserById(leaderboard.get(index).getKey()).queue(user -> {
                var entry = leaderboard.get(index);
                String newDescription = description;
                if(user != null) newDescription += (MessageFormat.format(bundle.getString("Leaderboard"), index + 1,
                        user.getAsMention(), entry.getValue()));
                sendLeaderboard(index +1, newDescription, message, bundle);
            });
        }
    }

    private ArrayList<Map.Entry<Long, Integer>> getLeaderboard() {
        System.out.println(points);
        var set = points.entrySet();
        var list = new ArrayList<>(set);
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        return list;
    }

    public ResourceBundle getBundle(Session session){
        return ResourceBundle.getBundle("locale.game.WhatIsIt", Linwood.getInstance().getDatabase().getGuildById(session, game.getGuildId()).getLocalization());
    }

    public long getTextChannelId() {
        return textChannelId;
    }

    public TextChannel getTextChannel(){
        return Linwood.getInstance().getJda().getTextChannelById(textChannelId);
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

    public HashSet<Long> getWantWriter() {
        return wantWriter;
    }

    public Long getWantWriterMessageId() {
        return wantWriterMessageId;
    }

    public boolean wantWriter(Session session, Member member) {
        if(getRound() != null)
            return false;
        wantWriter.add(member.getIdLong());
        getTextChannel().sendMessage(MessageFormat.format(getBundle(session).getString("Join"), member.getUser().getAsMention())).queue();
        return true;
    }

    public void removeWriter(Session session, Member member) {
        wantWriter.remove(member.getIdLong());
        getTextChannel().sendMessage(MessageFormat.format(getBundle(session).getString("Leave"), member.getUser().getAsMention())).queue();
    }
}
