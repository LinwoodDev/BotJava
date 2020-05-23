package com.github.codedoctorde.linwood.commands.fun;

import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.commands.CommandManager;
import com.github.codedoctorde.linwood.entity.ServerEntity;

import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class FunCommand extends CommandManager {
    @Override
    public Command[] commands() {
        return new Command[]{
            new WindowsCommand()
        };
    }

    @Override
    public String[] aliases(ServerEntity entity) {
        return new String[]{
                "fun", "f", "funny"
        };
    }

    @Override
    public ResourceBundle getBundle(ServerEntity entity) {
        return ResourceBundle.getBundle("locale.commands.fun.Base", entity.getLocalization());
    }
}
