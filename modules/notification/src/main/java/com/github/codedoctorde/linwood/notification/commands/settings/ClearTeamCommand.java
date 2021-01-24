package com.github.codedoctorde.linwood.notification.commands.settings;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.notification.entity.NotificationEntity;
import net.dv8tion.jda.api.Permission;

/**
 * @author CodeDoctorDE
 */
public class ClearTeamCommand extends Command {
    @Override
    public boolean onCommand(final CommandEvent event) {
        var entity = event.getEntity();
        if(event.getArguments().length != 0)
            return false;
        event.getGuildEntity(NotificationEntity.class).setTeamRole(null);
        entity.save(event.getSession());
        event.reply(getTranslationString(entity, "Clear")).queue();
        return true;
    }
    @Override
    public boolean hasPermission(final CommandEvent event) {
       var member = event.getMember();
       var entity = event.getEntity();
       return member.hasPermission(Permission.MANAGE_SERVER) || entity.getMaintainerId() != null && member.getRoles().contains(member.getGuild().getRoleById(entity.getMaintainerId()));
    }

    public ClearTeamCommand() {
        super(
                "clearteam",
                "clear-team",
                "cleart",
                "clear-t"
        );
    }
}
