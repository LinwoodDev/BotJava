package com.github.codedoctorde.linwood.commands.settings.karma;

import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.commands.CommandManager;
import com.github.codedoctorde.linwood.entity.GuildEntity;

import java.util.*;

public class KarmaSettingsCommand extends CommandManager {
    @Override
    public Command[] commands() {
        return new Command[]{
                new LikeCommand(),
                new ClearLikeCommand(),
                new DislikeCommand(),
                new ClearDislikeCommand()
        };
    }

    @Override
    public Set<String> aliases(GuildEntity entity) {
        return new HashSet<>(Collections.singletonList(
                "karma"
        ));
    }

    @Override
    public @org.jetbrains.annotations.NotNull ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.commands.settings.karma.Base");
    }
}
