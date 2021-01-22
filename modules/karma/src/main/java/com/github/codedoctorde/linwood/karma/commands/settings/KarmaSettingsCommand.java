package com.github.codedoctorde.linwood.karma.commands.settings;

import com.github.codedoctorde.linwood.core.commands.CommandManager;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class KarmaSettingsCommand extends CommandManager {
    public KarmaSettingsCommand() {
        super(
                "karma"
        );
        registerCommands(
                new LikeCommand(),
                new ClearLikeCommand(),
                new DislikeCommand(),
                new ClearDislikeCommand()
        );
    }
}
