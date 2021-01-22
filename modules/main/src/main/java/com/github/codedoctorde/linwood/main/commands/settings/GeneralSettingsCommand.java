package com.github.codedoctorde.linwood.main.commands.settings;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandManager;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
