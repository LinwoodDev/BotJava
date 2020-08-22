package com.github.codedoctorde.linwood.game.commands.settings;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandManager;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

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
    public @NotNull Set<String> aliases(GuildEntity entity) {
        return new HashSet<>(Arrays.asList(
                "game",
                "games",
                "gm"
        ));
    }

    @Override
    public @NotNull ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.com.github.codedoctorde.linwood.karma.commands.settings.game.Base", entity.getLocalization());
    }
}
