package com.github.codedoctorde.linwood.core.utils;

import com.github.codedoctorde.linwood.core.Linwood;
import com.github.codedoctorde.linwood.core.config.ActivityConfig;
import net.dv8tion.jda.api.OnlineStatus;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author CodeDoctorDE
 */
public class ActivityChanger {
    private Timer timer;
    private int index;
    private OnlineStatus status;
    private final List<ActivityConfig> activities = new ArrayList<>();
    private int period = 1000 * 10;
    public ActivityChanger(){
    }

    public void start(){
        timer = new Timer();
        if(activities.isEmpty())
            Linwood.getInstance().getJda().setPresence(status, activities.get(index).build(Linwood.getInstance().getVersion(), Linwood.getInstance().getJda().getGuilds().size(),
                    Linwood.getInstance().getJda().getUsers().size()));
        else
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(Linwood.getInstance().getJda() != null) {
                        if (index < 0 || index >= activities.size()) index = 0;
                        if(activities.size() <= 0) return;
                        Linwood.getInstance().getJda().setPresence(status, activities.get(index).build(Linwood.getInstance().getVersion(), Linwood.getInstance().getJda().getGuilds().size(),
                                Linwood.getInstance().getJda().getUsers().size()));
                        index++;
                    }
                }
            }, 0, period);
    }
    public void cancel(){
        timer.cancel();
    }
    public void restart(){
        cancel();
        start();
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

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        if(period > 1000)
            this.period = period;
    }

    public List<ActivityConfig> getActivities() {
        return activities;
    }

    public OnlineStatus getStatus() {
        return status;
    }

    public void setStatus(OnlineStatus status) {
        this.status = status;
    }

    public void reload(){
        activities.clear();
        activities.addAll(Arrays.stream(Linwood.getInstance().getModules()).flatMap(module -> module.getActivities().stream()).collect(Collectors.toList()));
    }

}
