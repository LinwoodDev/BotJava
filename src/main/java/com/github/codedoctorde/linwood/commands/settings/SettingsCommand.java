package com.github.codedoctorde.linwood.commands.settings;

import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.commands.CommandManager;
import com.github.codedoctorde.linwood.commands.settings.game.*;
import com.github.codedoctorde.linwood.commands.settings.general.GeneralSettingsCommand;
import com.github.codedoctorde.linwood.commands.settings.general.LanguageCommand;
import com.github.codedoctorde.linwood.commands.settings.general.PrefixCommand;
import com.github.codedoctorde.linwood.commands.settings.karma.KarmaSettingsCommand;
import com.github.codedoctorde.linwood.entity.GuildEntity;

import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class SettingsCommand extends CommandManager {
    @Override
    public Command[] commands() {
        return new Command[]{
                new GameSettingsCommand(),
                new GeneralSettingsCommand(),
                new KarmaSettingsCommand()
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
