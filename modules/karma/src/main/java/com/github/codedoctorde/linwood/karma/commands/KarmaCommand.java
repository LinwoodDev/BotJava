package com.github.codedoctorde.linwood.karma.commands;

import com.github.codedoctorde.linwood.core.commands.CommandManager;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class KarmaCommand extends CommandManager {
    public KarmaCommand(){
        super(
                "karma"
        );
        registerCommands(
                new KarmaInfoCommand(),
                new KarmaLeaderboardCommand()
        );
    }
}
