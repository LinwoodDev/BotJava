package com.github.codedoctorde.linwood.game.commands.settings;

import com.github.codedoctorde.linwood.core.commands.CommandManager;

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
