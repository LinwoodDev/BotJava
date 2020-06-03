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

    public Game startGame(long guildId, GameMode gameMode){
        stopGame(guildId);
        var game = new Game(games.size(), guildId, gameMode);
        games.add(game);
        game.start();
        return game;
    }
    public void stopGame(long guildId){
        games.stream().filter(game -> game.getGuildId() == guildId).forEach(game -> stopGame(game, false));
        games.clear();
    }
    public void stopGame(Game game){
        stopGame(game, true);
    }
    private void stopGame(Game game, boolean remove){
        if(!games.contains(game))
            return;
        game.stop();
        if(remove)
            games.remove(game);
    }
    @Nullable
    public Game getGame(long guildId){
        Game current = null;
        for (Game game:
             games)
            if (game.getGuildId() == guildId)
                current = game;
        return current;
    }
    public void clearGames(){
        games.forEach(game -> stopGame(game, false));
        games.clear();
    }

    public Game[] getGames() {
        return games.toArray(new Game[0]);
    }
}
