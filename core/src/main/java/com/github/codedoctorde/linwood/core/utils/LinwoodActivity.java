package com.github.codedoctorde.linwood.core.utils;

import com.github.codedoctorde.linwood.core.Linwood;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author CodeDoctorDE
 */
public class LinwoodActivity {
    private OnlineStatus status = OnlineStatus.ONLINE;
    private Activity.ActivityType type = Activity.ActivityType.WATCHING;

    public LinwoodActivity(){
    }

    public void updateStatus(){
        Linwood.getInstance().getJda().setPresence(status, Activity.of(type, String.format(String.join(" | ", getActivities()), Linwood.getInstance().getVersion(), Linwood.getInstance().getJda().getGuilds().size(),
                                Linwood.getInstance().getJda().getUsers().size())));
    }

    public List<String> getActivities() {
        return Arrays.stream(Linwood.getInstance().getModules()).flatMap(module -> module.getActivities().stream()).collect(Collectors.toList());
    }

    public Activity.ActivityType getType() {
        return type;
    }

    public void setType(Activity.ActivityType type) {
        this.type = type;
    }

    public OnlineStatus getStatus() {
        return status;
    }

    public void setStatus(OnlineStatus status) {
        this.status = status;
    }
}
