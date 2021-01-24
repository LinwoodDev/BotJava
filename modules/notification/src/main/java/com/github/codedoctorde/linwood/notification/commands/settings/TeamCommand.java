package com.github.codedoctorde.linwood.notification.commands.settings;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.notification.entity.NotificationEntity;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;

public class TeamCommand extends Command {
    @Override
    public boolean onCommand(final CommandEvent event) {
        var args = event.getArguments();
        var entity = event.getEntity();
        var notificationEntity = event.getGuildEntity(NotificationEntity.class);
        if(args.length > 1)
            return false;
        if(args.length == 0)
            if(notificationEntity.getTeamRoleId() != null)
                event.replyFormat(getTranslationString(entity, "Get"), notificationEntity.getTeamRole().getName(), notificationEntity.getTeamRoleId()).queue();
            else
                event.reply(getTranslationString(entity, "GetNull")).queue();
        else {
            try {
                Role role = null;
                try{
                    role = event.getMessage().getGuild().getRoleById(args[0]);
                }catch(Exception ignored){ }
                if(role == null){
                    var roles = event.getMessage().getGuild().getRolesByName(args[0], true);
                    if(roles.size() < 1)
                        event.reply(getTranslationString(entity, "SetNothing")).queue();
                    else if(roles.size() > 1)
                        event.reply(getTranslationString(entity, "SetMultiple")).queue();
                    else
                        role = roles.get(0);
                }
                if(role == null)
                    return true;
                notificationEntity.setTeamRole(role);
                entity.save(event.getSession());
                event.replyFormat(getTranslationString(entity, "Set"), notificationEntity.getTeamRole().getName(), notificationEntity.getTeamRoleId()).queue();
            }catch(NullPointerException e){
                event.reply(getTranslationString(entity, "NotValid")).queue();
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

    public TeamCommand() {
        super(
                "team",
                "t"
        );
    }
}
