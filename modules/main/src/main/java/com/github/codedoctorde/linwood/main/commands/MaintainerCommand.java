package com.github.codedoctorde.linwood.main.commands;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;

import java.util.ResourceBundle;

public class MaintainerCommand extends Command {
    @Override
    public boolean onCommand(final CommandEvent event) {
        var entity = event.getEntity();
        var args = event.getArguments();
        if(args.length > 1)
            return false;
        if(args.length == 0)
            if(entity.getMaintainerId() != null)
                event.replyFormat(getTranslationString(entity, "Get"), entity.getMaintainer().getName(), entity.getMaintainerId()).queue();
            else
                event.reply(getTranslationString(entity, "GetNull")).queue();
        else {
            try {
                Role role = null;
                try{
                    role = event.getGuild().getRoleById(args[0]);
                }catch(Exception ignored){ }
                if(role == null){
                    var roles = event.getGuild().getRolesByName(args[0], true);
                    if(roles.size() < 1)
                        event.reply(getTranslationString(entity, "SetNothing")).queue();
                    else if(roles.size() > 1)
                        event.reply(getTranslationString(entity, "SetMultiple")).queue();
                    else
                        role = roles.get(0);
                }
                if(role == null)
                    return true;
                entity.setMaintainer(role);
                entity.save(event.getSession());
                event.replyFormat(getTranslationString(entity, "Set"), entity.getMaintainer().getName(), entity.getMaintainerId()).queue();
            }catch(NullPointerException e){
                event.reply(getTranslationString(entity, "NotValid")).queue();
            }
        }
        return true;
    }
    @Override
    public boolean hasPermission(final CommandEvent event) {
        var member = event.getMember();
        return member.hasPermission(Permission.MANAGE_SERVER);
    }

    public MaintainerCommand() {
        super(
                "maintainer",
                "maint",
                "controller",
                "control"
        );
    }
}