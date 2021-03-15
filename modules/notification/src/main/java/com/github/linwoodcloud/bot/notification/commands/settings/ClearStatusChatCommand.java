package com.github.linwoodcloud.bot.notification.commands.settings;

import com.github.linwoodcloud.bot.core.commands.Command;
import com.github.linwoodcloud.bot.core.commands.CommandEvent;
import com.github.linwoodcloud.bot.core.exceptions.CommandSyntaxException;
import com.github.linwoodcloud.bot.notification.entity.NotificationEntity;

/**
 * @author CodeDoctorDE
 */
public class ClearStatusChatCommand extends Command {
    @Override
    public void onCommand(final CommandEvent event) {
        var entity = event.getEntity();
        if(event.getArguments().length != 0)
            throw new CommandSyntaxException(this);
        event.getGuildEntity(NotificationEntity.class).setStatusChat(null);
        entity.save();
        event.reply(translate(entity, "Clear")).queue();
    }


    public ClearStatusChatCommand() {
        super(
                "clearstatus",
                "clear-status",
                "clearstatuschat",
                "clear-statuschat",
                "clearstatus-chat",
                "clear-status-chat",
                "clears",
                "clear-s",
                "clearsc",
                "clear-sc"
        );
    }
}
