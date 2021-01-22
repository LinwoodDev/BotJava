package com.github.codedoctorde.linwood.core.commands;

import com.github.codedoctorde.linwood.core.Linwood;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

/**
 * @author CodeDoctorDE
 */
public abstract class CommandManager extends Command {
    protected Set<Command> commands = new HashSet<>();

    protected CommandManager(String... aliases){
        super(aliases);
    }

    @Override
    public boolean onCommand(final CommandEvent event) {
        var entity = event.getEntity();
        var message = event.getMessage();
        var args = event.getArguments();
        var session = event.getSession();
        var baseBundle = getBaseBundle(entity);
        for (Command command : commands)
            if (command.hasAlias(
                    (event.getArguments().length != 0) ? args[0].toLowerCase() : "")) {
                if(command.hasPermission(event.upper()) || Linwood.getInstance().getConfig().getOwners().contains(message.getAuthor().getIdLong())) {
                    if (!command.onCommand(event.upper()))
                        event.replyFormat(ResourceBundle.getBundle("locale.Command").getString("Syntax"), Objects.requireNonNull(command.getBundle(entity)).getString("Syntax")).queue();
                }
                else
                    event.reply(baseBundle.getString("NoPermission")).queue();
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

    public Set<Command> getCommands() {
        return new HashSet<>(commands);
    }

    public void registerCommands(Command... current){
        commands.addAll(Arrays.asList(current));
    }

    public void registerCommand(Command command){
        commands.add(command);
    }
    public void unregisterCommand(Command command){
        commands.remove(command);
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
                        .addField(bundle.getString("Syntax"), commandBundle.containsKey("Syntax")?commandBundle.getString("Syntax"):"", false)
                        .build())
                .build();
        event.reply(output).queue();
    }
}
