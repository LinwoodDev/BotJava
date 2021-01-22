package com.github.codedoctorde.linwood.game.commands;

import com.github.codedoctorde.linwood.core.commands.CommandManager;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author CodeDoctorDE
 */
public class GameCommand extends CommandManager {
    public GameCommand() {
        super(
                "game", "games", "play"
        );
        registerCommands(
                new StopGameCommand(),
                new WhatIsItCommand(),
                new TicTacToeCommand()
        );
    }
}
