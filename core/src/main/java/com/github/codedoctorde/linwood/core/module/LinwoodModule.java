package com.github.codedoctorde.linwood.core.module;

import com.github.codedoctorde.linwood.core.commands.CommandImplementer;

import java.util.*;

/**
 * @author CodeDoctorDE
 */
public abstract class LinwoodModule {
    private final Set<Object> listeners = new HashSet<>();
    private final Set<CommandImplementer> commandImplementers = new HashSet<>();

    public Object[] getListeners() {
        return listeners.toArray(new Object[0]);
    }

    public Object[] getCommands() {
        return commandImplementers.toArray(new Object[0]);
    }
    protected boolean registerEvents(Object... eventListeners){
        return listeners.addAll(Arrays.asList(eventListeners));
    }
    protected boolean unregisterEvents(Object... eventListeners){
        return listeners.removeAll(Arrays.asList(eventListeners));
    }
    protected boolean registerCommands(CommandImplementer... registeredCommandImplementers){
        return commandImplementers.addAll(Arrays.asList(registeredCommandImplementers));
    }
    protected boolean unregisterCommands(CommandImplementer... registeredCommandImplementers){
        return commandImplementers.removeAll(Arrays.asList(registeredCommandImplementers));
    }
    public abstract void onEnable();
    public abstract void onDisable();
}
