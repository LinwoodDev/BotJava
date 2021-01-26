package com.github.codedoctorde.linwood.main.commands;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import net.dv8tion.jda.api.Permission;

/**
 * @author CodeDoctorDE
 */
public class AddPrefixCommand extends Command {
    @Override
    public boolean onCommand(final CommandEvent event) {
        var entity = event.getEntity();
        var args = event.getArguments();
        if(event.getArguments().length != 1)
            return false;
        else try {
            if(!entity.addPrefix(args[0])){
                event.reply(translate(entity, "Invalid")).queue();
                return true;
            }
            entity.save(event.getSession());
            event.replyFormat(translate(entity, "Success"), args[0]).queue();
        } catch (NullPointerException e) {
            event.reply(translate(entity, "NotValid")).queue();
        }
        return true;
    }
    @Override
    public boolean hasPermission(final CommandEvent event) {
       var member = event.getMember();
       var entity = event.getEntity();
       return member.hasPermission(Permission.MANAGE_SERVER) || entity.getMaintainerId() != null && member.getRoles().contains(member.getGuild().getRoleById(entity.getMaintainerId()));
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
