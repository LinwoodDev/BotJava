package com.github.codedoctorde.linwood.commands.settings;

import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.commands.CommandManager;
import com.github.codedoctorde.linwood.commands.fun.WindowsCommand;
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
                new PrefixCommand()
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
