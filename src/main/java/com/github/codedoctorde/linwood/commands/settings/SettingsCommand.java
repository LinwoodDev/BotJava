package com.github.codedoctorde.linwood.commands.settings;

import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.commands.CommandManager;
import com.github.codedoctorde.linwood.commands.fun.WindowsCommand;
import com.github.codedoctorde.linwood.entity.ServerEntity;

import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class SettingsCommand extends CommandManager {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("locale.commands.settings.Base");
    @Override
    public Command[] commands() {
        return new Command[]{
            new WindowsCommand()
        };
    }

    @Override
    public String[] aliases() {
        return new String[]{
                "conf", "config", "setting", "settings"
        };
    }

    @Override
    public String description(ServerEntity entity) {
        return bundle.getString("Description");
    }

    @Override
    public String syntax(ServerEntity entity) {
        return bundle.getString("Syntax");
    }
}
