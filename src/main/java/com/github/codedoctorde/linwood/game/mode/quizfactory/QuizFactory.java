package com.github.codedoctorde.linwood.game.mode.quizfactory;

import com.github.codedoctorde.linwood.Main;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import com.github.codedoctorde.linwood.game.Game;
import com.github.codedoctorde.linwood.game.GameMode;
import org.hibernate.Session;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class QuizFactory implements GameMode {
    private final long ownerId;
    private Game game;
    private long voiceChannelId;
    private long textChannelId;
    private final List<String> questions = new ArrayList<>();

    public QuizFactory(long ownerId){
        this.ownerId = ownerId;
    }

    @Override
    public void start(Game game) {
        this.game = game;

        var session = Main.getInstance().getDatabase().getSessionFactory().openSession();
        var guild = GuildEntity.get(session, game.getGuildId());
        var category = Main.getInstance().getJda().getCategoryById(guild.getGameCategoryId());
        if(category == null) {
            game.stop();
            return;
        }
        var bundle = getBundle(session);
        category.createVoiceChannel(MessageFormat.format(bundle.getString("VoiceChannel"), game.getId())).queue((voiceChannel -> this.voiceChannelId = voiceChannel.getIdLong()));
        category.createTextChannel(MessageFormat.format(bundle.getString("TextChannel"), game.getId())).queue((textChannel -> this.textChannelId = textChannel.getIdLong()));
    }

    public void startIngame(Session session){
        Objects.requireNonNull(Main.getInstance().getJda().getTextChannelById(textChannelId)).
                sendMessage(getBundle(session).getString("Ingame")).queue();
    }

    @Override
    public void stop() {

    }

    public long getOwnerId() {
        return ownerId;
    }

    public Game getGame() {
        return game;
    }

    public ResourceBundle getBundle(Session session){
        return ResourceBundle.getBundle("locale.game.QuizFactory", Main.getInstance().getDatabase().getGuildById(session, game.getGuildId()).getLocalization());
    }

    public List<String> getQuestions() {
        return questions;
    }
}
