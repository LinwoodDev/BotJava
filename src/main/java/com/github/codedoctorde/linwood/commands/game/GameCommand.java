package com.github.codedoctorde.linwood.commands.game;

import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.commands.CommandManager;
import com.github.codedoctorde.linwood.commands.fun.WindowsCommand;
import com.github.codedoctorde.linwood.entity.GuildEntity;

import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class GameCommand extends CommandManager {
    @Override
    public Command[] commands() {
        return new Command[]{
                new StopGameCommand(),
                new WhatIsItCommand()
        };
    }

    @Override
    public String[] aliases(GuildEntity entity) {
        return new String[]{
                "game", "games", "play"
        };
    }

    @Override
    public ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.commands.game.Base", entity.getLocalization());
    }
}
