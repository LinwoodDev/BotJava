package com.github.codedoctorde.linwood.main.commands;

import com.github.codedoctorde.linwood.core.Linwood;
import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

/**
 * @author CodeDoctorDE
 */
public class CommandHelpCommand extends Command {
    @Override
    public void onCommand(final CommandEvent event) {
        if(event.getArguments().length == 0)
            return;
        var command = Linwood.getInstance().getCommandListener().findCommand(event.getArgumentsString());
        event.reply(new EmbedBuilder().setTitle(command.translate(event.getEntity(), "Syntax")).setDescription(command.translate(event.getEntity(), "Description")).build());
    }

    public CommandHelpCommand() {
        super(
                "help",
                "h"
        );
    }
}
