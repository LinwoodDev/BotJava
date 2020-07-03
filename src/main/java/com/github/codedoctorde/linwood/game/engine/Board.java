package com.github.codedoctorde.linwood.game.engine;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CodeDoctorDE
 */
public class Board {
    private final BufferedImage backgroundImage;
    private final Vector2 fieldSize;
    public final List<Actor> actors = new ArrayList<>();

    public Board(BufferedImage backgroundImage, Vector2 fieldSize){
        this.backgroundImage = backgroundImage;
        this.fieldSize = fieldSize;
    }

    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

    public Vector2 getFieldSize() {
        return fieldSize;
    }
}
