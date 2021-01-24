package com.github.codedoctorde.linwood.fun.fun;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;

import java.util.*;

/**
 * @author CodeDoctorDE
 */
public class DiceCommand extends Command {
    private final Random random = new Random();
    @Override
    public boolean onCommand(final CommandEvent event) {
        if(event.getArguments().length != 0)
            return false;
        event.getMessage().getChannel().sendMessageFormat(getTranslationString(entity, "Output"), random.nextInt(5) + 1).queue();
        return true;
    }

    public DiceCommand() {
        super(
                "dice",
                "d"
        );
    }
}
