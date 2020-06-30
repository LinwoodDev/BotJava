package com.github.codedoctorde.linwood.config;

import net.dv8tion.jda.api.entities.Activity;

/**
 * @author CodeDoctorDE
 */
public class ActivityConfig {
    private String name;
    private Type type = Type.WATCHING;
    public ActivityConfig(){

    }
    public ActivityConfig(Type type, String name){
        this.name = name;
        this.type = type;
    }

    public Activity build(){
        switch (type){
            case PLAYING:
                return Activity.playing(name);
            case WATCHING:
                return Activity.watching(name);
            case LISTENING:
                return Activity.listening(name);
            default:
                return null;
        }
    }
    public enum Type {
        PLAYING,
        WATCHING,
        LISTENING
    }
}
