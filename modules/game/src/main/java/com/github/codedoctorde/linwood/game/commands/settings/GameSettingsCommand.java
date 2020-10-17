package com.github.codedoctorde.linwood.game.commands.settings;

import com.github.codedoctorde.linwood.core.commands.CommandManager;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GameSettingsCommand extends CommandManager {
    public Command[] commands() {
        return new Command[]{
                new ClearGameCategoryCommand(),
                new GameCategoryCommand(),
                new ClearGameMasterCommand(),
                new GameMasterCommand()
        };
    }

    @Override
    public @NotNull Set<String> aliases(GuildEntity entity) {
        super(
                "game",
                "games",
                "gm"
        );
    }
}
