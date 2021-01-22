package com.github.codedoctorde.linwood.main.commands.settings;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import net.dv8tion.jda.api.Permission;

import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class LanguageCommand extends Command {
    @Override
    public boolean onCommand(final CommandEvent event) {
        var args = event.getArguments();
        var entity = event.getEntity();
        ResourceBundle bundle = getBundle(entity);
        if(args.length > 1)
            return false;
        if(args.length == 0)
            event.replyFormat(bundle.getString("Get"), entity.getLocalization().getDisplayName(entity.getLocalization())).queue();
        else {
            try {
                entity.setLocale(args[0]);
                entity.save(event.getSession());
                event.replyFormat(bundle.getString("Set"), entity.getLocalization().getDisplayName(entity.getLocalization())).queue();
            }catch(NullPointerException e){
                event.reply(bundle.getString("NotValid")).queue();
            }
        }
        return true;
    }
    @Override
    public boolean hasPermission(final CommandEvent event) {
        var member = event.getMember();
        var entity = event.getEntity();
        return member.hasPermission(Permission.MANAGE_SERVER) || entity.getMaintainerId() != null && member.getRoles().contains(member.getGuild().getRoleById(entity.getMaintainerId()));
    }

    public LanguageCommand() {
        super(
                "language",
                "locale",
                "lang"
        );
    }
}
