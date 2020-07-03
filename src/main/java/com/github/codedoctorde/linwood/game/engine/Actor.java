package com.github.codedoctorde.linwood.game.engine;

import com.github.codedoctorde.linwood.game.engine.Transform;
import com.github.codedoctorde.linwood.game.engine.Vector2;

import java.awt.image.BufferedImage;

/**
 * @author CodeDoctorDE
 */
public abstract class Actor {
    private Transform transform;
    private BufferedImage image;

    abstract void act();

    public Transform getTransform() {
        return transform;
    }

    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage render(){

    }
}
