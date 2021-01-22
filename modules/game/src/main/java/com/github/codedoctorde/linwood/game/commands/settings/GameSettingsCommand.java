package com.github.codedoctorde.linwood.game.commands.settings;

import com.github.codedoctorde.linwood.core.commands.CommandManager;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GameSettingsCommand extends CommandManager {
    public GameSettingsCommand(){
        super(
                "game",
                "games",
                "gm"
        );
        registerCommands(
                new ClearGameCategoryCommand(),
                new GameCategoryCommand(),
                new ClearGameMasterCommand(),
                new GameMasterCommand()
        );
    }
}
