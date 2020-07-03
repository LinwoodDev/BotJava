package com.github.codedoctorde.linwood.game.engine;

import com.github.codedoctorde.linwood.utils.ImageUtil;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CodeDoctorDE
 */
public abstract class World {
    private final List<Actor> actors = new ArrayList<>();
    private Vector2 worldSize;
    private Vector2 cellSize;
    protected BufferedImage backgroundImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);

    public World(Vector2 worldSize, Vector2 cellSize){
        this.worldSize = worldSize;
        this.cellSize = cellSize;
    }

    public Vector2 getCellSize() {
        return cellSize;
    }

    public Vector2 getWorldSize() {
        return worldSize;
    }

    public void setCellSize(Vector2 cellSize) {
        this.cellSize = cellSize;
    }

    public void setWorldSize(Vector2 worldSize) {
        this.worldSize = worldSize;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

    public BufferedImage render(){
        act();
        actors.forEach(Actor::act);
        var renderedImage = ImageUtil.clone(backgroundImage);
        var graphics = backgroundImage.createGraphics();
        actors.forEach(actor -> graphics.drawImage(actor.render(), actor.getTransform().getLocation().getX(), actor.getTransform().getLocation().getY(),
                actor.getTransform().getScale().getX(), actor.getTransform().getScale().getY(), null));
        return renderedImage;
    }

    public abstract void act();
}
