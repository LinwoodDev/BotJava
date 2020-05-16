package com.github.codedoctorde.linwood.commands;

import com.github.codedoctorde.linwood.commands.fun.FunCommand;
import com.github.codedoctorde.linwood.entity.ServerEntity;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;

import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class BaseCommand extends CommandManager {
    private final InfoCommand infoCommand = new InfoCommand();
    private static final ResourceBundle bundle = ResourceBundle.getBundle("locale.commands.Base");
    @Override
    public Command[] commands() {
        return new Command[]{
                infoCommand,
                new HelpCommand(),
                new FunCommand()
        };
    }

    @Override
    public String[] aliases(ServerEntity entity) {
        return new String[0];
    }

    @Override
    public String description(ServerEntity entity) {
        return bundle.getString("Description");
    }

    public void runInfo(Session session, ServerEntity entity, Message message) {
        infoCommand.onCommand(session, message, entity, "", new String[0]);
    }

    @Override
    public String syntax(ServerEntity entity) {
        return bundle.getString("Syntax");
    }
}
