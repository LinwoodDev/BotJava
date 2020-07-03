package com.github.codedoctorde.linwood.game.engine;

import com.github.codedoctorde.linwood.utils.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author CodeDoctorDE
 */
public abstract class World {
    private final List<Actor> actors = new ArrayList<>();
    private Vector2 cellSize;
    private BufferedImage backgroundImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);

    public World(Vector2 cellSize){
        this.cellSize = cellSize;
        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/assets/tictactoe/background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Vector2 getCellSize() {
        return cellSize;
    }

    public void setCellSize(Vector2 cellSize) {
        this.cellSize = cellSize;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(BufferedImage backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public BufferedImage render(){
        act();
        actors.forEach(Actor::act);
        var renderedImage = ImageUtil.clone(backgroundImage);
        var graphics = renderedImage.getGraphics();
        actors.forEach(actor -> graphics.drawImage(actor.render(), actor.getTransform().getLocation().getX() * cellSize.getX(), actor.getTransform().getLocation().getY() * cellSize.getY(),
                actor.getTransform().getScale().getX(), actor.getTransform().getScale().getY(), null));
        graphics.dispose();
        return renderedImage;
    }

    public abstract void act();
}
