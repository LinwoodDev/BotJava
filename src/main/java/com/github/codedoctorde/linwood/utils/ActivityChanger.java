package com.github.codedoctorde.linwood.utils;

import com.github.codedoctorde.linwood.Main;
import net.dv8tion.jda.api.entities.Activity;

import java.util.*;

/**
 * @author CodeDoctorDE
 */
public class ActivityChanger {
    private Timer timer;
    private int index;
    private List<Activity> activities = new ArrayList<>();
    public ActivityChanger(Activity... activities){
        this.activities = Arrays.asList(activities);
    }

    public void start(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(Main.getInstance().getJda() != null) {
                    if (index < 0 || index >= activities.size()) index = 0;
                    Main.getInstance().getJda().getPresence().setActivity(activities.get(index));
                    index++;
                }
            }
        }, 0, 1000 * 10);
    }
    public void cancel(){
        timer.cancel();
    }
    public boolean isRunning(){
        return timer != null;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<Activity> getActivities() {
        return activities;
    }
}