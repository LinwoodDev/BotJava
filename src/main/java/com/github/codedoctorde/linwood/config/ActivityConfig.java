package com.github.codedoctorde.linwood.config;

import net.dv8tion.jda.api.entities.Activity;

import java.text.MessageFormat;

/**
 * @author CodeDoctorDE
 */
public class ActivityConfig {
    private String name;
    private Activity.ActivityType type = Activity.ActivityType.WATCHING;
    public ActivityConfig(){

    }
    public ActivityConfig(Activity.ActivityType type, String name){
        this.name = name;
        this.type = type;
    }

    public Activity build(Object... format){
        return Activity.of(type, MessageFormat.format(name, format));
    }

    public Activity.ActivityType getType() {
        return type;
    }
}
