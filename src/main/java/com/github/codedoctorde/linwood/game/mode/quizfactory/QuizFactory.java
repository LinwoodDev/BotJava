package com.github.codedoctorde.linwood.game.mode.quizfactory;

import com.github.codedoctorde.linwood.Main;
import com.github.codedoctorde.linwood.entity.ServerEntity;
import com.github.codedoctorde.linwood.game.Game;
import com.github.codedoctorde.linwood.game.GameMode;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.hooks.SubscribeEvent;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class QuizFactory implements GameMode {
    private final int ownerId;
    private Game game;
    private long voiceChannelId;
    private long textChannelId;
    private List<String> questions = new ArrayList<>();

    public QuizFactory(int ownerId){
        this.ownerId = ownerId;
    }

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
        var bundle = getBundle(server);
        category.createVoiceChannel(MessageFormat.format(bundle.getString("VoiceChannel"), game.getId())).queue((voiceChannel -> this.voiceChannelId = voiceChannel.getIdLong()));
        category.createTextChannel(MessageFormat.format(bundle.getString("TextChannel"), game.getId())).queue((textChannel -> this.textChannelId = textChannel.getIdLong()));
    }

    @Override
    public void stop() {

    }

    public int getOwnerId() {
        return ownerId;
    }

    public Game getGame() {
        return game;
    }

    public ResourceBundle getBundle(ServerEntity entity){
        return ResourceBundle.getBundle("locale.game.QuizFactory", entity.getLocalization());
    }

    public List<String> getQuestions() {
        return questions;
    }
}
