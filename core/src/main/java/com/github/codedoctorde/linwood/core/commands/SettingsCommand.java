package com.github.codedoctorde.linwood.core.commands;

import com.github.codedoctorde.linwood.core.commands.settings.GeneralSettingsCommand;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author CodeDoctorDE
 */
public class SettingsCommand extends CommandManager {
    public SettingsCommand(){
        commands.add(new GeneralSettingsCommand());
    }

    @Override
    public @NotNull Set<String> aliases(GuildEntity entity) {
        return new HashSet<>(Arrays.asList(
                "conf", "config", "setting", "settings"
        ));
    }

    @Override
    public @NotNull ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.com.github.codedoctorde.linwood.karma.commands.settings.Base", entity.getLocalization());
    }
}
