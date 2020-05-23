package com.github.codedoctorde.linwood.commands.game;

import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.entity.ServerEntity;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;

import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class QuizFactoryCommand implements Command {

    @Override
    public boolean onCommand(Session session, Message message, ServerEntity entity, String label, String[] args) {
        return false;
    }

    @Override
    public String[] aliases(ServerEntity entity) {
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


    public ResourceBundle getBundle(ServerEntity entity){
        return ResourceBundle.getBundle("locale.commands.game.QuizFactory", entity.getLocalization());
    }
}
