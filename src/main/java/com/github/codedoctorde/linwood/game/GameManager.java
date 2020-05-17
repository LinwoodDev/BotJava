package com.github.codedoctorde.linwood.game;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CodeDoctorDE
 */
public class GameManager {
    private final int serverId;
    public List<Game> games = new ArrayList<>();

    public GameManager(int serverId){
        this.serverId = serverId;
    }

    public int getServerId() {
        return serverId;
    }

    public Game startGame(GameMode gameMode){
        var game = new Game(games.size(), serverId, gameMode);
        games.add(game);
        return game;
    }
    public void stopGame(Game game){
        game.stop();
        games.remove(game);
    }
}
