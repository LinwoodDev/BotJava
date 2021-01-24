package com.github.codedoctorde.linwood.template.commands;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;

/**
 * @author CodeDoctorDE
 */
public class SendCommand extends Command {
    @Override
    public boolean onCommand(final CommandEvent event) {
        return false;
    }

    public SendCommand() {
        super(
                "write",
                "w"
        );
    }
}
