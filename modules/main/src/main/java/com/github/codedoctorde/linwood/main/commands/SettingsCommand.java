package com.github.codedoctorde.linwood.main.commands;

import com.github.codedoctorde.linwood.core.commands.CommandManager;

/**
 * @author CodeDoctorDE
 */
public class SettingsCommand extends CommandManager {
    public SettingsCommand(){
        super(
                "conf", "config", "setting", "settings"
        );
        registerCommand(new GeneralSettingsCommand());
    }
}
