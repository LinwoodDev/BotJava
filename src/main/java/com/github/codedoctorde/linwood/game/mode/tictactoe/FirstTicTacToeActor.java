package com.github.codedoctorde.linwood.game.mode.tictactoe;

import com.github.codedoctorde.linwood.game.engine.Actor;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

/**
 * @author CodeDoctorDE
 */
public class FirstTicTacToeActor extends Actor {
    public FirstTicTacToeActor(){
        try {
            setImage(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("assets/tictactoe/first.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void act() {

    }
}
