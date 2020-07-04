package com.github.codedoctorde.linwood.game.engine;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CodeDoctorDE
 */
public abstract class Board {
    private World currentWorld = null;
    public Board(){

    }

    public World getCurrentWorld() {
        return currentWorld;
    }

    public void setCurrentWorld(World currentWorld) {
        this.currentWorld = currentWorld;
        onChangeWorld();
    }
    public BufferedImage render(){
        if(currentWorld != null)
            return currentWorld.render();
        return null;
    }
    protected void onChangeWorld(){

    }
    public abstract void act();
}
