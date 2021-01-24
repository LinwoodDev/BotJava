package com.github.codedoctorde.linwood.fun.fun;

import com.github.codedoctorde.linwood.core.commands.CommandManager;

/**
 * @author CodeDoctorDE
 */
public class FunCommand extends CommandManager {
    public FunCommand() {
        super(
                "fun", "f", "funny"
        );
        registerCommands(
                new WindowsCommand(),
                new DiceCommand()
        );
    }
}
