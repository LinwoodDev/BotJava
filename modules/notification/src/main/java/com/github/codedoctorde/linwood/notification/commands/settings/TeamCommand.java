package com.github.codedoctorde.linwood.notification.commands.settings;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class TeamCommand extends Command {
    @Override
    public boolean onCommand(final CommandEvent event) {
        var args = event.getArguments();
        var entity = event.getEntity();
        ResourceBundle bundle = getBundle(entity);
        if(args.length > 1)
            return false;
        if(args.length == 0)
            if(entity.getNotificationEntity().getTeamRoleId() != null)
                event.replyFormat(bundle.getString("Get"), entity.getNotificationEntity().getTeamRole().getName(), entity.getNotificationEntity().getTeamRoleId()).queue();
            else
                event.reply(bundle.getString("GetNull")).queue();
        else {
            try {
                Role role = null;
                try{
                    role = event.getMessage().getGuild().getRoleById(args[0]);
                }catch(Exception ignored){ }
                if(role == null){
                    var roles = event.getMessage().getGuild().getRolesByName(args[0], true);
                    if(roles.size() < 1)
                        event.reply(bundle.getString("SetNothing")).queue();
                    else if(roles.size() > 1)
                        event.reply(bundle.getString("SetMultiple")).queue();
                    else
                        role = roles.get(0);
                }
                if(role == null)
                    return true;
                entity.getNotificationEntity().setTeamRole(role);
                entity.save(event.getSession());
                event.replyFormat(bundle.getString("Set"), entity.getNotificationEntity().getTeamRole().getName(), entity.getNotificationEntity().getTeamRoleId()).queue();
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

    public TeamCommand() {
        super(
                "team",
                "t"
        );
    }
}
