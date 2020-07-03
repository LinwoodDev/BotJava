package com.github.codedoctorde.linwood.game.engine;

/**
 * @author CodeDoctorDE
 */
public class Transform {
    private Vector2 location;
    private Vector2 rotation;
    private Vector2 scale;

    public Vector2 getLocation() {
        return location;
    }

    public Vector2 getRotation() {
        return rotation;
    }

    public Vector2 getScale() {
        return scale;
    }

    public void setLocation(Vector2 location) {
        this.location = location;
    }

    public void setRotation(Vector2 rotation) {
        this.rotation = rotation;
    }

    public void setScale(Vector2 scale) {
        this.scale = scale;
    }
}
