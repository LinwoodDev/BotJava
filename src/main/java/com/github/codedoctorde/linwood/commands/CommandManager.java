package com.github.codedoctorde.linwood.commands;

import com.github.codedoctorde.linwood.entity.GuildEntity;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;

import java.text.MessageFormat;
import java.util.*;

/**
 * @author CodeDoctorDE
 */
public abstract class CommandManager implements Command {
    public abstract Command[] commands();

    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        var bundle = getBundle(entity);
        var baseBundle = getBaseBundle(entity);
        for (Command command : commands())
            if (Arrays.asList(command.aliases(entity)).contains(
                    (args.length > 0) ? args[0].toLowerCase() : "")) {
                if(command.hasPermission(message.getMember()))
                if(!command.onCommand(session, message, entity,
                        (args.length > 0) ? args[0] : "",
                        (args.length > 0) ? Arrays.copyOfRange(args, 1, args.length) : new String[0]))
                    message.getChannel().sendMessage(MessageFormat.format(ResourceBundle.getBundle("locale.Command").getString("Syntax"), Objects.requireNonNull(command.getBundle(entity)).getString("Syntax"))).queue();
                else
                    message.getChannel().sendMessage(baseBundle.getString("NoPermission")).queue();
                return true;
            }
        if(args.length <= 0 && bundle != null)message.getChannel().sendMessage(bundle.containsKey("Description")?bundle.getString("Description"): Objects.requireNonNull(getBundle(entity)).getString("Syntax")).queue();
        else
            return false;
        return true;
    }

    public Command getCommand(GuildEntity entity, String... args){
        Command command = this;
        for (String arg:
                args)
            if (command instanceof CommandManager)
                for (Command current :
                        ((CommandManager) command).commands())
                    if (Arrays.asList(current.aliases(entity)).contains(arg.toLowerCase())) command = current;
        return command;
    }
    private ResourceBundle getBaseBundle(GuildEntity entity){
        return ResourceBundle.getBundle("locale.Command", entity.getLocalization());
    }
}
