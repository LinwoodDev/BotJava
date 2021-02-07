package com.github.linwoodcloud.bot.main.commands.settings;

import com.github.linwoodcloud.bot.core.commands.Command;
import com.github.linwoodcloud.bot.core.commands.CommandEvent;
import com.github.linwoodcloud.bot.core.exceptions.CommandSyntaxException;

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
