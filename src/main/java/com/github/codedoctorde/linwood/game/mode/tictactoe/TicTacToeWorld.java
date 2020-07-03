package com.github.codedoctorde.linwood.game.mode.tictactoe;

import com.github.codedoctorde.linwood.game.engine.Vector2;
import com.github.codedoctorde.linwood.game.engine.World;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

/**
 * @author CodeDoctorDE
 */
public class TicTacToeWorld extends World {
    public TicTacToeWorld() {
        super(new Vector2(100, 100));
        try {
            setBackgroundImage(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("assets/tictactoe/background.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void act() {
        getActors().add(new FirstTicTacToeActor(){{getTransform().setLocation(0, 0);}});
        getActors().add(new FirstTicTacToeActor(){{getTransform().setLocation(2, 0);}});
        getActors().add(new FirstTicTacToeActor(){{getTransform().setLocation(0, 1);}});
        getActors().add(new FirstTicTacToeActor(){{getTransform().setLocation(1, 2);}});
        getActors().add(new FirstTicTacToeActor(){{getTransform().setLocation(2, 2);}});
        getActors().add(new SecondTicTacToeActor(){{getTransform().setLocation(1, 0);}});
        getActors().add(new SecondTicTacToeActor(){{getTransform().setLocation(2, 1);}});
        getActors().add(new SecondTicTacToeActor(){{getTransform().setLocation(0, 2);}});
        getActors().add(new SecondTicTacToeActor(){{getTransform().setLocation(1, 1);}});
    }
}
