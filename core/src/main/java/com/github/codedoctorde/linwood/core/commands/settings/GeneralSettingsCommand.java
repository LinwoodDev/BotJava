package com.github.codedoctorde.linwood.core.commands.settings;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandManager;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
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

    @Override
    public @NotNull ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.com.github.codedoctorde.linwood.karma.commands.settings.general.Base", entity.getLocalization());
    }
}
