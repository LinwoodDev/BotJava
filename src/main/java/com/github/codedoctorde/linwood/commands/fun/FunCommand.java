package com.github.codedoctorde.linwood.commands.fun;

import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.commands.CommandManager;
import com.github.codedoctorde.linwood.entity.GuildEntity;

import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class FunCommand extends CommandManager {
    @Override
    public Command[] commands() {
        return new Command[]{
                new WindowsCommand(),
                new DiceCommand(),
                new KarmaCommand()
        };
    }

    @Override
    public String[] aliases(GuildEntity entity) {
        return new String[]{
                "fun", "f", "funny"
        };
    }

    @Override
    public ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.commands.fun.Base", entity.getLocalization());
    }
}
