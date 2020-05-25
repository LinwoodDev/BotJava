package com.github.codedoctorde.linwood.game.mode.whatisit;

import com.github.codedoctorde.linwood.Main;
import com.github.codedoctorde.linwood.game.Game;
import com.github.codedoctorde.linwood.game.GameMode;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import org.hibernate.Session;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class WhatIsIt implements GameMode {
    private Game game;
    private WhatIsItRound round;
    private long textChannelId;
    private HashMap<Long, Integer> points = new HashMap<>();

    @Override
    public void start(Game game) {
        this.game = game;

        var session = Main.getInstance().getDatabase().getSessionFactory().openSession();
        var server = Main.getInstance().getDatabase().getServerById(session, game.getServerId());
        var category = Main.getInstance().getJda().getCategoryById(server.getGameCategoryId());
        if(category == null) {
            game.stop();
            return;
        }
        var bundle = getBundle(session);
        category.createTextChannel(MessageFormat.format(bundle.getString("TextChannel"), game.getId())).queue((textChannel -> this.textChannelId = textChannel.getIdLong()));
    }

    @Override
    public void stop() {

    }

    public void chooseNextPlayer(){

    }
    public void nextRound(Session session, int writerId, String word){
        round = new WhatIsItRound(writerId, word, this);
        var bundle = getBundle(session);

        getTextChannel().sendMessage(bundle.getString("NextRound")).queue();
    }
    public void finishRound(){

    }

    public ResourceBundle getBundle(Session session){
        return ResourceBundle.getBundle("locale.game.WhatIsIt", Main.getInstance().getDatabase().getServerById(session, game.getServerId()).getLocalization());
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
    public void addPoint(Member member, int number){
        points.put(member.getIdLong(), points.getOrDefault(member.getIdLong(), 0) + number);
    }
    public int getPoints(Member member){
        return points.getOrDefault(member.getIdLong(), 0);
    }
}
