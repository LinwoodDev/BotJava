package com.github.codedoctorde.linwood.commands.game;

import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.commands.CommandManager;
import com.github.codedoctorde.linwood.commands.game.quizfactory.CreateQuizFactoryCommand;
import com.github.codedoctorde.linwood.entity.ServerEntity;

import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class QuizFactoryCommand extends CommandManager {
    @Override
    public Command[] commands() {
        return new Command[]{
                new CreateQuizFactoryCommand()
        };
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

    @Override
    public String description(ServerEntity entity) {
        return getBundle(entity).getString("Description");
    }

    @Override
    public String syntax(ServerEntity entity) {
        return getBundle(entity).getString("Syntax");
    }

    public ResourceBundle getBundle(ServerEntity entity){
        return ResourceBundle.getBundle("locale.commands.game.QuizFactory", entity.getLocalization());
    }
}
