package com.github.codedoctorde.linwood.apps.single.game.mode.tictactoe;

import com.github.codedoctorde.linwood.apps.single.game.engine.Actor;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

/**
 * @author CodeDoctorDE
 */
public class CircleTicTacToeActor extends Actor {
    public CircleTicTacToeActor(){
        try {
            setImage(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("assets/tictactoe/circle.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void act() {
        
    }
}
