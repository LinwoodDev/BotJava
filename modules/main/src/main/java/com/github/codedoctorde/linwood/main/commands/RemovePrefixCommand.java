package com.github.codedoctorde.linwood.main.commands;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.core.exceptions.CommandSyntaxException;

public class RemovePrefixCommand extends Command {
    @Override
    public void onCommand(final CommandEvent event) {
        var args = event.getArguments();
        var entity = event.getEntity();
        if(args.length != 1)
            throw new CommandSyntaxException(this);
        if(!entity.getPrefixes().remove(args[0])){
            event.reply(translate(entity, "Invalid")).queue();
            return;
        }
        entity.save(event.getSession());
        event.replyFormat(translate(entity, "Success"), args[0]).queue();
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
