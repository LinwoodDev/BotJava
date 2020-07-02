package com.github.codedoctorde.linwood.commands.settings;

import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.commands.CommandManager;
import com.github.codedoctorde.linwood.commands.settings.game.ClearGameCategoryCommand;
import com.github.codedoctorde.linwood.commands.settings.game.ClearGameMasterCommand;
import com.github.codedoctorde.linwood.commands.settings.game.GameCategoryCommand;
import com.github.codedoctorde.linwood.commands.settings.game.GameMasterCommand;
import com.github.codedoctorde.linwood.entity.GuildEntity;

import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class SettingsCommand extends CommandManager {
    @Override
    public Command[] commands() {
        return new Command[]{
                new LanguageCommand(),
                new PrefixCommand(),
                new GameCategoryCommand(),
                new ClearGameCategoryCommand(),
                new GameMasterCommand(),
                new ClearGameMasterCommand()
        };
    }

    @Override
    public String[] aliases(GuildEntity entity) {
        return new String[]{
                "conf", "config", "setting", "settings"
        };
    }

    @Override
    public ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.commands.settings.Base", entity.getLocalization());
    }
}
