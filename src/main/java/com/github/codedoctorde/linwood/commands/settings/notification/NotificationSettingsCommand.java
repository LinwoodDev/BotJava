package com.github.codedoctorde.linwood.commands.settings.notification;

import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.commands.CommandManager;
import com.github.codedoctorde.linwood.commands.settings.karma.ClearDislikeCommand;
import com.github.codedoctorde.linwood.commands.settings.karma.ClearLikeCommand;
import com.github.codedoctorde.linwood.commands.settings.karma.DislikeCommand;
import com.github.codedoctorde.linwood.commands.settings.karma.LikeCommand;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class NotificationSettingsCommand extends CommandManager {
    @Override
    public Command[] commands() {
        return new Command[]{
                new ClearTeamCommand(),
                new TeamCommand(),
                new ClearSupportChatCommand(),
                new SupportChatCommand(),
                new ClearStatusChatCommand(),
                new StatusChatCommand()
        };
    }

    @Override
    public Set<String> aliases(GuildEntity entity) {
        return new HashSet<>(Arrays.asList(
                "notification",
                "notifications",
                "notif",
                "n"
        ));
    }

    @Override
    public @Nullable ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.commands.settings.notification.Base");
    }
}
