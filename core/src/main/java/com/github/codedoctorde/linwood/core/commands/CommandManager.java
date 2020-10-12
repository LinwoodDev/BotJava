package com.github.codedoctorde.linwood.core.commands;

import com.github.codedoctorde.linwood.core.Linwood;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author CodeDoctorDE
 */
public abstract class CommandManager extends Command {
    protected Set<Command> commands = new HashSet<>();

    @Override
    public boolean onCommand(final CommandEvent event) {
        var entity = event.getEntity();
        var message = event.getMessage();
        var args = event.getArgs();
        var session = event.getSession();
        var baseBundle = getBaseBundle(entity);
        for (Command command : commands)
            if (command.hasAlias(
                    (args.length > 0) ? args[0].toLowerCase() : "")) {
                if(command.hasPermission(message.getMember(), entity, session) || Linwood.getInstance().getConfig().getOwners().contains(message.getAuthor().getIdLong())) {
                    if (!command.onCommand(event.upper()))
                        message.getChannel().sendMessageFormat(ResourceBundle.getBundle("locale.Command").getString("Syntax"), Objects.requireNonNull(command.getBundle(entity)).getString("Syntax")).queue();
                }
                else
                    message.getChannel().sendMessage(baseBundle.getString("NoPermission")).queue();
                return true;
            }
        if(args.length <= 0) sendHelp(event);
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
                        ((CommandManager) command).commands)
                    if (current.hasAlias(arg.toLowerCase())) command = current;
        return command;
    }
    public Command getCommand(Class<? extends Command> commandClass){
        return commands.stream().filter(command -> command.getClass().equals(commandClass)).findFirst().orElse(null);
    }
    private ResourceBundle getBaseBundle(GuildEntity entity){
        return ResourceBundle.getBundle("locale.Command", entity.getLocalization());
    }

    public Set<Command> getCommands() {
        return commands;
    }

    public void sendHelp(CommandEvent event){
        var commandBundle = getBundle(event.getEntity());
        var bundle = getBaseBundle(event.getEntity());
        var output = new MessageBuilder()
                .append(" ")
                .setEmbed(new EmbedBuilder()
                        .setTitle(bundle.getString("HelpTitle"))
                        .setDescription(commandBundle.containsKey("Description")?commandBundle.getString("Description"):"")
                        .setColor(new Color(0x3B863B))
                        .setTimestamp(LocalDateTime.now())
                        .setFooter(null, null)
                        .addField("Aliases", String.join(", ", getAliases()), true)
                        .addField("Permissions", commandBundle.containsKey("Permission")?commandBundle.getString("Permission"):"", true)
                        .addField("Syntax", commandBundle.containsKey("Syntax")?commandBundle.getString("Syntax"):"", false)
                        .build())
                .build();
        event.reply(output).queue();
    }
}
