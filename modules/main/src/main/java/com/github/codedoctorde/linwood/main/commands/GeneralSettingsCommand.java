package com.github.codedoctorde.linwood.main.commands;

import com.github.codedoctorde.linwood.core.commands.CommandManager;

public class GeneralSettingsCommand extends CommandManager {

    public GeneralSettingsCommand() {
        super(
                "general",
                "gen",
                "g"
        );
        registerCommands(
                new LanguageCommand(),
                new PrefixesCommand(),
                new AddPrefixCommand(),
                new RemovePrefixCommand()
        );
    }
}
