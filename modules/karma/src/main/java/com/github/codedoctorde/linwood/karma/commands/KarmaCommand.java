package com.github.codedoctorde.linwood.karma.commands;

import com.github.codedoctorde.linwood.core.commands.CommandManager;

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
