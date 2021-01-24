package com.github.codedoctorde.linwood.game.commands;

import com.github.codedoctorde.linwood.core.commands.CommandManager;

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
