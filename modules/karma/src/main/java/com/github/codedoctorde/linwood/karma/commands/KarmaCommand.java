package com.github.codedoctorde.linwood.karma.commands;

import com.github.codedoctorde.linwood.core.commands.CommandManager;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class KarmaCommand extends CommandManager {
    public @NotNull Command[] commands() {
        return new Command[]{
                new KarmaInfoCommand(),
                new KarmaLeaderboardCommand()
        };
    }

    @Override
    public @NotNull Set<String> aliases(GuildEntity entity) {
        return new HashSet<>(Collections.singletonList(
                "karma"
        );
    }
}
