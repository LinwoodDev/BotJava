package com.github.codedoctorde.linwood.fun.fun;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.core.exceptions.CommandSyntaxException;

import java.util.*;

/**
 * @author CodeDoctorDE
 */
public class DiceCommand extends Command {
    private final Random random = new Random();
    @Override
    public void onCommand(final CommandEvent event) {
        if(event.getArguments().length != 0)
            throw new CommandSyntaxException(this);
        var entity = event.getEntity();
        event.getMessage().getChannel().sendMessageFormat(translate(entity, "Output"), random.nextInt(5) + 1).queue();
    }

    public DiceCommand() {
        super(
                "dice",
                "d"
        );
    }
}
