package com.github.codedoctorde.linwood.core.commands;

import com.github.codedoctorde.linwood.core.commands.settings.GeneralSettingsCommand;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author CodeDoctorDE
 */
public class SettingsCommand extends CommandManager {
    public SettingsCommand(){
        commandImplementers.add(new GeneralSettingsCommand());
    }

    @Override
    public @NotNull Set<String> aliases(GuildEntity entity) {
        return new HashSet<>(Arrays.asList(
                "conf", "config", "setting", "settings"
        ));
    }
}
