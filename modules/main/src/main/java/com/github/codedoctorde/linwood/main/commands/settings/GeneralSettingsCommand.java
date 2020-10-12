package com.github.codedoctorde.linwood.main.commands.settings;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandManager;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GeneralSettingsCommand extends CommandManager {
    public Command[] commands() {
        return new Command[]{
                new LanguageCommand(),
                new PrefixesCommand(),
                new AddPrefixCommand(),
                new RemovePrefixCommand()
        };
    }

    @Override
    public @NotNull Set<String> aliases(GuildEntity entity) {
        return new HashSet<>(Arrays.asList(
                "general",
                "gen",
                "g"
        ));
    }
}
