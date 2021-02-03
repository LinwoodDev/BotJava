package com.github.codedoctorde.linwood.main.commands.settings;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.core.exceptions.CommandSyntaxException;
import net.dv8tion.jda.api.Permission;

/**
 * @author CodeDoctorDE
 */
public class AddPrefixCommand extends Command {
    @Override
    public void onCommand(final CommandEvent event) {
        var entity = event.getEntity();
        var args = event.getArguments();
        if(event.getArguments().length != 1)
            throw new CommandSyntaxException(this);
        else try {
            if(!entity.addPrefix(args[0])){
                event.reply(translate(entity, "Invalid")).queue();
                return;
            }
            entity.save(event.getSession());
            event.replyFormat(translate(entity, "Success"), args[0]).queue();
        } catch (NullPointerException e) {
            event.reply(translate(entity, "NotValid")).queue();
        }
    }

    public AddPrefixCommand() {
        super(
                "addprefix",
                "addpre-fix",
                "add-prefix",
                "add-pre-fix"
        );
    }
}
