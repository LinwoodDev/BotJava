package com.github.codedoctorde.linwood.main.commands;

import com.github.codedoctorde.linwood.core.Linwood;
import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;

/**
 * @author CodeDoctorDE
 */
public class HelpCommand extends Command {
    @Override
    public boolean onCommand(final CommandEvent event) {
        if(event.getArguments().length == 0)
            return false;
        var command = Linwood.getInstance().getCommandListener().findCommand(event.getArgumentsString());

        return true;
    }

    public HelpCommand() {
        super(
                "help",
                "h"
        );
    }
}
