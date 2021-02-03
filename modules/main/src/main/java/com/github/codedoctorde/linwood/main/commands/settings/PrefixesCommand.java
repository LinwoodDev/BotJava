package com.github.codedoctorde.linwood.main.commands.settings;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.core.exceptions.CommandSyntaxException;
import net.dv8tion.jda.api.Permission;

public class PrefixesCommand extends Command {
    @Override
    public void onCommand(final CommandEvent event) {
        if(event.getArguments().length != 0)
            throw new CommandSyntaxException(this);
        var entity = event.getEntity();
        event.replyFormat(translate(entity, "Get"), String.join("," , entity.getPrefixes())).queue();
    }

    public PrefixesCommand() {
        super(
                "prefixes",
                "pre-fixes",
                "list-prefixes",
                "list-pre-fixes",
                "listprefixes",
                "listpre-fixes"
        );
    }

}
