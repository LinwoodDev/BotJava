package com.github.codedoctorde.linwood.commands.settings.game;

import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.commands.CommandManager;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import org.jetbrains.annotations.Nullable;

import java.util.ResourceBundle;

public class GameSettingsCommand extends CommandManager {
    @Override
    public Command[] commands() {
        return new Command[]{
                new ClearGameCategoryCommand(),
                new GameCategoryCommand(),
                new ClearGameMasterCommand(),
                new GameMasterCommand()
        };
    }

    @Override
    public String[] aliases(GuildEntity entity) {
        return new String[]{
                "game",
                "games",
                "gm"
        };
    }

    @Override
    public @Nullable ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.commands.settings.game.Base", entity.getLocalization());
    }
}
