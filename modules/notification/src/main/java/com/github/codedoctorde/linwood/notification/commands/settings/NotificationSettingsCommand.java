package com.github.codedoctorde.linwood.notification.commands.settings;

import com.github.codedoctorde.linwood.core.commands.CommandManager;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class NotificationSettingsCommand extends CommandManager {
    public NotificationSettingsCommand() {
        super(
                "notification",
                "notifications",
                "notif",
                "n"
        );
        registerCommands(
                new ClearTeamCommand(),
                new TeamCommand(),
                new ClearSupportChatCommand(),
                new SupportChatCommand(),
                new ClearStatusChatCommand(),
                new StatusChatCommand()
        );
    }
}
