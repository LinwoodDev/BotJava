package com.github.codedoctorde.linwood.game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CodeDoctorDE
 */
public class Board {
    private final BufferedImage backgroundImage;
    private final Size fieldSize;
    public final List<Actor> actors = new ArrayList<>();

    public Board(BufferedImage backgroundImage, Size fieldSize){
        this.backgroundImage = backgroundImage;
        this.fieldSize = fieldSize;
    }

    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }
}
