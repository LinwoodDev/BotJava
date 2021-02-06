package com.github.codedoctorde.linwood.main.commands;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.core.exceptions.CommandSyntaxException;

public class MemberInfoCommand extends Command {
    @Override
    public void onCommand(CommandEvent event) {
        if(event.getArguments().length != 1)
            throw new CommandSyntaxException(this);

    }
}
