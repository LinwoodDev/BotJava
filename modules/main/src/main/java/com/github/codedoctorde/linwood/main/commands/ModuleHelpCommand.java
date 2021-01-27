package com.github.codedoctorde.linwood.main.commands;

import com.github.codedoctorde.linwood.core.Linwood;
import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.core.module.LinwoodModule;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 * @author CodeDoctorDE
 */
public class ModuleHelpCommand extends Command {
    @Override
    public boolean onCommand(final CommandEvent event) {
        if(event.getArguments().length == 0)
            return false;
        var entity = event.getEntity();
        var module = Linwood.getInstance().getModule(event.getArgumentsString());
        if(module != null) {
            var builder = new EmbedBuilder().setTitle(module.getName()).addField(translate(entity, "Commands"), null, false).setDescription(module.translate(entity, "Description"));
            module.getCommands().forEach(command -> builder.addField(command.translate(entity, "Syntax"), command.translate(entity, "Description"), true));
            builder.addField(translate(entity, "SettingsCommands"), translate(entity, "SettingsCommandsNote"), false);
            module.getSettingsCommands().forEach(command -> builder.addField(command.translate(entity, "Syntax"), command.translate(entity, "Description"), true));
            event.reply(builder
                    .build()).queue();
        }
        else
            event.reply(translate(entity, "HelpNotFound")).queue();
        return true;
    }

    public ModuleHelpCommand() {
        super(
                "help",
                "h"
        );
    }
}
