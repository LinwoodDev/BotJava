package com.github.codedoctorde.linwood.game;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CodeDoctorDE
 */
public class GameManager {
    public List<Game> games = new ArrayList<>();

    public GameManager(){
    }

    public Game startGame(int serverId, GameMode gameMode){
        stopGame(serverId);
        var game = new Game(games.size(), serverId, gameMode);
        games.add(game);
        return game;
    }
    public void stopGame(int serverId){
        games.stream().filter(game -> game.getServerId() == serverId).forEach(this::stopGame);
    }
    public void stopGame(Game game){
        game.stop();
        games.remove(game);
    }
    @Nullable
    public Game getGame(int serverId){
        Game current = null;
        for (Game game:
             games)
            if (game.getServerId() == serverId)
                current = game;
        return current;
    }
    public void clearGames(){
        games.forEach(this::stopGame);
    }

    public Game[] getGames() {
        return games.toArray(new Game[0]);
    }
}
