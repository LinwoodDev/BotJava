package com.github.codedoctorde.linwood.apps.single.game.mode.quizfactory;

import com.github.codedoctorde.linwood.Linwood;
import com.github.codedoctorde.linwood.apps.single.SingleApplication;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import com.github.codedoctorde.linwood.apps.single.SingleApplicationMode;
import org.hibernate.Session;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class QuizFactory implements SingleApplicationMode {
    private final long ownerId;
    private SingleApplication game;
    private long voiceChannelId;
    private long textChannelId;
    private final List<String> questions = new ArrayList<>();

    public QuizFactory(long ownerId){
        this.ownerId = ownerId;
    }

    @Override
    public void start(SingleApplication app) {
        this.game = app;

        var session = Linwood.getInstance().getDatabase().getSessionFactory().openSession();
        var guild = GuildEntity.get(session, game.getGuildId());
        var category = guild.getGameEntity().getGameCategory();
        if(category == null) {
            game.stop();
            return;
        }
        var bundle = getBundle(session);
        category.createVoiceChannel(MessageFormat.format(bundle.getString("VoiceChannel"), game.getId())).queue((voiceChannel -> this.voiceChannelId = voiceChannel.getIdLong()));
        category.createTextChannel(MessageFormat.format(bundle.getString("TextChannel"), game.getId())).queue((textChannel -> this.textChannelId = textChannel.getIdLong()));
    }

    public void startIngame(Session session){
        Objects.requireNonNull(Linwood.getInstance().getJda().getTextChannelById(textChannelId)).
                sendMessage(getBundle(session).getString("Ingame")).queue();
    }

    @Override
    public void stop() {

    }

    public long getOwnerId() {
        return ownerId;
    }

    public SingleApplication getGame() {
        return game;
    }

    public ResourceBundle getBundle(Session session){
        return ResourceBundle.getBundle("locale.game.QuizFactory", Linwood.getInstance().getDatabase().getGuildById(session, game.getGuildId()).getLocalization());
    }

    public List<String> getQuestions() {
        return questions;
    }
}
