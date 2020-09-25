package com.github.codedoctorde.linwood.core.utils;

import com.github.codedoctorde.linwood.core.Linwood;
import com.github.codedoctorde.linwood.core.config.ActivityConfig;

import java.util.*;

/**
 * @author CodeDoctorDE
 */
public class ActivityChanger {
    private Timer timer;
    private int index;
    private final List<ActivityConfig> activities;
    public ActivityChanger(ActivityConfig... activities){
        this.activities =new ArrayList<>(Arrays.asList(activities));
    }

    public void start(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(Linwood.getInstance().getJda() != null) {
                    if (index < 0 || index >= activities.size()) index = 0;
                    if(activities.size() <= 0) return;
                    Linwood.getInstance().getJda().getPresence().setActivity(activities.get(index).build(Linwood.getInstance().getVersion(), Linwood.getInstance().getJda().getGuilds().size(),
                            Linwood.getInstance().getJda().getUsers().size()));
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

    public List<ActivityConfig> getActivities() {
        return activities;
    }

}
