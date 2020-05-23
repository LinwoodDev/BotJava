package com.github.codedoctorde.linwood.commands;

import com.github.codedoctorde.linwood.entity.ServerEntity;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public abstract class CommandManager implements Command {
    public abstract Command[] commands();

    @Override
    public boolean onCommand(Session session, Message message, ServerEntity entity, String label, String[] args) {
        for (Command command : commands())
            if (Arrays.asList(command.aliases(entity)).contains(
                    (args.length > 0) ? args[0].toLowerCase() : "")) {
                if(!command.onCommand(session, message, entity,
                        (args.length > 0) ? args[0] : "",
                        (args.length > 0) ? Arrays.copyOfRange(args, 1, args.length) : new String[0]))
                    message.getChannel().sendMessage(command.getBundle(entity).getString("Syntax")).queue();
                return true;
            }
        return false;
    }

    public Command getCommand(ServerEntity entity, String... args){
        Command command = this;
        for (String arg:
             args) {
            for (Command current:
                 commands())
                if (Arrays.asList(current.aliases(entity)).contains(arg)) {
                    command = current;
                    if (command instanceof CommandManager)
                        command = ((CommandManager) command).getCommand(entity, Arrays.copyOfRange(args, 1, args.length));
                    break;
                }else
                    return null;
        }
        return command;
    }
}
