package com.github.codedoctorde.linwood.config;

import com.github.codedoctorde.linwood.Main;
import net.dv8tion.jda.api.entities.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CodeDoctorDE
 */
public class MainConfig {
    private String prefix = "+lw";
    private List<ActivityConfig> activities = new ArrayList<>(){{
        add(new ActivityConfig(ActivityConfig.Type.LISTENING, "CodeDoctor"));
        add(new ActivityConfig(ActivityConfig.Type.WATCHING, "github/CodeDoctorDE"));
        add(new ActivityConfig(ActivityConfig.Type.PLAYING, "games with players :D"));
    }};
    private String supportURL = "https://discord.gg/a2vubnD";

    public MainConfig(){

    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public List<ActivityConfig> getActivities() {
        return activities;
    }

    public String getSupportURL() {
        return supportURL;
    }

    public void setSupportURL(String supportURL) {
        this.supportURL = supportURL;
    }
}
