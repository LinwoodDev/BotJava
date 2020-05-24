package com.github.codedoctorde.linwood.game.mode.whatisit;

import com.github.codedoctorde.linwood.Main;
import com.github.codedoctorde.linwood.game.Game;
import com.github.codedoctorde.linwood.game.GameMode;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import org.hibernate.Session;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class WhatIsIt implements GameMode {
    private Game game;
    private long voiceChannelId;
    private long textChannelId;
    private WhatIsItRound round;

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
        category.createVoiceChannel(MessageFormat.format(bundle.getString("VoiceChannel"), game.getId())).queue((voiceChannel -> this.voiceChannelId = voiceChannel.getIdLong()));
        category.createTextChannel(MessageFormat.format(bundle.getString("TextChannel"), game.getId())).queue((textChannel -> this.textChannelId = textChannel.getIdLong()));
    }

    @Override
    public void stop() {

    }

    public void chooseNextPlayer(){

    }
    public void nextRound(){

    }

    public ResourceBundle getBundle(Session session){
        return ResourceBundle.getBundle("locale.game.WhatIsIt", Main.getInstance().getDatabase().getServerById(session, game.getServerId()).getLocalization());
    }

    public long getVoiceChannelId() {
        return voiceChannelId;
    }
    public VoiceChannel getVoiceChannel(){
        return Main.getInstance().getJda().getVoiceChannelById(voiceChannelId);
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
}
