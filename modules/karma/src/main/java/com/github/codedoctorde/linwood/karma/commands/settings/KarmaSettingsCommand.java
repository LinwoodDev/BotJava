package com.github.codedoctorde.linwood.karma.commands.settings;

import com.github.codedoctorde.linwood.core.commands.CommandManager;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class KarmaSettingsCommand extends CommandManager {
    public Command[] commands() {
        return new Command[]{
                new LikeCommand(),
                new ClearLikeCommand(),
                new DislikeCommand(),
                new ClearDislikeCommand()
        };
    }

    @Override
    public @NotNull Set<String> aliases(GuildEntity entity) {
        return new HashSet<>(Collections.singletonList(
                "karma"
        );
    }
}
