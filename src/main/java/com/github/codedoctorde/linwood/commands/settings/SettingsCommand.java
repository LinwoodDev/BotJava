package com.github.codedoctorde.linwood.commands.settings;

import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.commands.CommandManager;
import com.github.codedoctorde.linwood.commands.settings.game.*;
import com.github.codedoctorde.linwood.commands.settings.general.GeneralSettingsCommand;
import com.github.codedoctorde.linwood.commands.settings.karma.KarmaSettingsCommand;
import com.github.codedoctorde.linwood.commands.settings.notification.NotificationSettingsCommand;
import com.github.codedoctorde.linwood.entity.GuildEntity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author CodeDoctorDE
 */
public class SettingsCommand extends CommandManager {
    @Override
    public Command[] commands() {
        return new Command[]{
                new GameSettingsCommand(),
                new GeneralSettingsCommand(),
                new KarmaSettingsCommand(),
                new NotificationSettingsCommand()
        };
    }

    @Override
    public Set<String> aliases(GuildEntity entity) {
        return new HashSet<>(Arrays.asList(
                "conf", "config", "setting", "settings"
        ));
    }

    @Override
    public @org.jetbrains.annotations.NotNull ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.commands.settings.Base", entity.getLocalization());
    }
}
