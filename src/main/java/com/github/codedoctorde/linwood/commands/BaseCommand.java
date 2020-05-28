package com.github.codedoctorde.linwood.commands;

import com.github.codedoctorde.linwood.commands.fun.FunCommand;
import com.github.codedoctorde.linwood.commands.game.GameCommand;
import com.github.codedoctorde.linwood.commands.settings.SettingsCommand;
import com.github.codedoctorde.linwood.entity.ServerEntity;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;

import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class BaseCommand extends CommandManager {
    private final InfoCommand infoCommand = new InfoCommand();
    @Override
    public Command[] commands() {
        return new Command[]{
                infoCommand,
                new HelpCommand(),
                new FunCommand(),
                new GameCommand(),
                new SettingsCommand()
        };
    }

    @Override
    public String[] aliases(ServerEntity entity) {
        return new String[0];
    }

    @Override
    public ResourceBundle getBundle(ServerEntity entity) {
        return ResourceBundle.getBundle("locale.commands.Base", entity.getLocalization());
    }

    public void runInfo(Session session, ServerEntity entity, Message message) {
        infoCommand.onCommand(session, message, entity, "", new String[0]);
    }
}
