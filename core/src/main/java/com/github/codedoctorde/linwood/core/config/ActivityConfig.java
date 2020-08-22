package com.github.codedoctorde.linwood.core.config;

import net.dv8tion.jda.api.entities.Activity;

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
        var status = name;
        if(format != null)
            status = String.format(status, format);
        return Activity.of(type, status);
    }

    public Activity.ActivityType getType() {
        return type;
    }
}
