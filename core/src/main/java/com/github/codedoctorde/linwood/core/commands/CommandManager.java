package com.github.codedoctorde.linwood.core.commands;

import com.github.codedoctorde.linwood.core.Linwood;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;

import java.util.*;

/**
 * @author CodeDoctorDE
 */
public abstract class CommandManager implements CommandImplementer {
    protected Set<CommandImplementer> commandImplementers = new HashSet<>();

    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        var bundle = getBundle(entity);
        var baseBundle = getBaseBundle(entity);
        for (CommandImplementer commandImplementer : commandImplementers)
            if (commandImplementer.aliases(entity).contains(
                    (args.length > 0) ? args[0].toLowerCase() : "")) {
                if(commandImplementer.hasPermission(message.getMember(), entity, session) || Linwood.getInstance().getConfig().getOwners().contains(message.getAuthor().getIdLong())) {
                    if (!commandImplementer.onCommand(session, message, entity,
                            (args.length > 0) ? args[0] : "",
                            (args.length > 0) ? Arrays.copyOfRange(args, 1, args.length) : new String[0]))
                        message.getChannel().sendMessageFormat(ResourceBundle.getBundle("locale.Command").getString("Syntax"), Objects.requireNonNull(commandImplementer.getBundle(entity)).getString("Syntax")).queue();
                }
                else
                    message.getChannel().sendMessage(baseBundle.getString("NoPermission")).queue();
                return true;
            }
        if(args.length <= 0) Linwood.getInstance().getBaseCommand().getHelpCommand().sendHelp(entity, this, message.getTextChannel());
        else
            return false;
        return true;
    }

    public CommandImplementer getCommand(GuildEntity entity, String... args){
        CommandImplementer commandImplementer = this;
        for (String arg:
                args)
            if (commandImplementer instanceof CommandManager)
                for (CommandImplementer current :
                        ((CommandManager) commandImplementer).commandImplementers)
                    if (current.aliases(entity).contains(arg.toLowerCase())) commandImplementer = current;
        return commandImplementer;
    }
    public CommandImplementer getCommand(Class<? extends CommandImplementer> commandClass){
        return commandImplementers.stream().filter(command -> command.getClass().equals(commandClass)).findFirst().orElse(null);
    }
    private ResourceBundle getBaseBundle(GuildEntity entity){
        return ResourceBundle.getBundle("locale.Command", entity.getLocalization());
    }

    public Set<CommandImplementer> getCommands() {
        return commandImplementers;
    }
}
