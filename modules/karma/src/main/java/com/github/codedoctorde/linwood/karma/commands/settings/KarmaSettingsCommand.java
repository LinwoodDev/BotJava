package com.github.codedoctorde.linwood.karma.commands.settings;

import com.github.codedoctorde.linwood.core.commands.CommandManager;

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
