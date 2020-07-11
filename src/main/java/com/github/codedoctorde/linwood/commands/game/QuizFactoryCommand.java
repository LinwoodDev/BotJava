package com.github.codedoctorde.linwood.commands.game;

import com.github.codedoctorde.linwood.Linwood;
import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import com.github.codedoctorde.linwood.apps.single.game.mode.quizfactory.QuizFactory;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;

import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class QuizFactoryCommand implements Command {

    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        if(args.length > 0)
        return false;
        Linwood.getInstance().getSingleApplicationManager().startGame(entity.getGuildId(), new QuizFactory(message.getAuthor().getIdLong()));
        message.getTextChannel().sendMessage(Objects.requireNonNull(getBundle(entity)).getString("Success")).queue();
        return true;
    }

    @Override
    public String[] aliases(GuildEntity entity) {
        return new String[]{
                "quiz",
                "quizfactory",
                "quiz-factory",
                "quizfac",
                "qfactory",
                "qfac",
                "qf"
        };
    }


    public ResourceBundle getBundle(GuildEntity entity){
        return ResourceBundle.getBundle("locale.commands.game.QuizFactory", entity.getLocalization());
    }
}
