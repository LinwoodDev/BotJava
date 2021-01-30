package com.github.codedoctorde.linwood.karma.commands;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.core.exceptions.CommandSyntaxException;

public class KarmaThanksCommand extends Command {
    @Override
    public void onCommand(final CommandEvent event) {
        throw new CommandSyntaxException(this);
    }

}
