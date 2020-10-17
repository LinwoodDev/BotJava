package com.github.codedoctorde.linwood.main.commands;

import com.github.codedoctorde.linwood.main.commands.settings.GeneralSettingsCommand;
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
        super(
                "conf", "config", "setting", "settings"
        );
    }
}
