package com.github.codedoctorde.linwood.main.commands;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;

public class RemovePrefixCommand extends Command {
    @Override
    public boolean onCommand(final CommandEvent event) {
        var args = event.getArguments();
        var entity = event.getEntity();
        if(args.length != 1)
        return false;
        if(!entity.getPrefixes().remove(args[0])){
            event.reply(translate(entity, "Invalid")).queue();
            return true;
        }
        entity.save(event.getSession());
        event.replyFormat(translate(entity, "Success"), args[0]).queue();
        return true;
    }

    public RemovePrefixCommand() {
        super(
                "removeprefix",
                "removepre-fix",
                "remove-prefix",
                "remove-pre-fix"
        );
    }
}
