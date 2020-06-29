package com.github.codedoctorde.linwood.game;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CodeDoctorDE
 */
public abstract class World {
    private final List<Actor> actors = new ArrayList<>();
    private Size worldSize;
    private Size cellSize;

    public World(Size worldSize, Size cellSize){
        this.worldSize = worldSize;
        this.cellSize = cellSize;
    }

    public Size getCellSize() {
        return cellSize;
    }

    public Size getWorldSize() {
        return worldSize;
    }

    public void setCellSize(Size cellSize) {
        this.cellSize = cellSize;
    }

    public void setWorldSize(Size worldSize) {
        this.worldSize = worldSize;
    }

    public List<Actor> getActors() {
        return actors;
    }
}
