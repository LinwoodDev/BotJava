package com.github.codedoctorde.linwood.commands.settings.general;

import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.commands.CommandManager;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import org.jetbrains.annotations.Nullable;

import java.util.ResourceBundle;

public class GeneralSettingsCommand extends CommandManager {
    @Override
    public Command[] commands() {
        return new Command[]{
                new LanguageCommand(),
                new PrefixCommand()
        };
    }

    @Override
    public String[] aliases(GuildEntity entity) {
        return new String[]{
                "general",
                "gen",
                "g"
        };
    }

    @Override
    public @Nullable ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.commands.settings.general.Base", entity.getLocalization());
    }
}
