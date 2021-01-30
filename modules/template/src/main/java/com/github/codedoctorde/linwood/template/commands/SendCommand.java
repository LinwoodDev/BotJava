package com.github.codedoctorde.linwood.template.commands;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.core.exceptions.CommandSyntaxException;

/**
 * @author CodeDoctorDE
 */
public class SendCommand extends Command {
    @Override
    public void onCommand(final CommandEvent event) {
        throw new CommandSyntaxException(this);
    }

    public SendCommand() {
        super(
                "write",
                "w"
        );
    }
}
