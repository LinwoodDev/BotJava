package com.github.codedoctorde.linwood.main.commands.settings;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.core.exceptions.CommandSyntaxException;
import net.dv8tion.jda.api.Permission;

/**
 * @author CodeDoctorDE
 */
public class LanguageCommand extends Command {
    @Override
    public void onCommand(final CommandEvent event) {
        var args = event.getArguments();
        var entity = event.getEntity();
        if(args.length > 1)
            throw new CommandSyntaxException(this);
        if(args.length == 0)
            event.replyFormat(translate(entity, "Get"), entity.getLocalization().getDisplayName(entity.getLocalization())).queue();
        else {
            try {
                entity.setLocale(args[0]);
                entity.save(event.getSession());
                event.replyFormat(translate(entity, "Set"), entity.getLocalization().getDisplayName(entity.getLocalization())).queue();
            }catch(NullPointerException e){
                event.reply(translate(entity, "NotValid")).queue();
            }
        }
    }

    public LanguageCommand() {
        super(
                "language",
                "locale",
                "lang"
        );
    }
}
