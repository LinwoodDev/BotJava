package com.github.linwoodcloud.bot.notification.commands.settings;

import com.github.linwoodcloud.bot.core.commands.Command;
import com.github.linwoodcloud.bot.core.commands.CommandEvent;
import com.github.linwoodcloud.bot.core.exceptions.CommandSyntaxException;
import com.github.linwoodcloud.bot.notification.entity.NotificationEntity;

/**
 * @author CodeDoctorDE
 */
public class ClearTeamCommand extends Command {
    @Override
    public void onCommand(final CommandEvent event) {
        var entity = event.getEntity();
        if(event.getArguments().length != 0)
            throw new CommandSyntaxException(this);
        event.getGuildEntity(NotificationEntity.class).setTeamRole(null);
        entity.save();
        event.reply(translate(entity, "Clear")).queue();
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
