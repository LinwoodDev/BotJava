package com.github.codedoctorde.linwood.core.module;

import com.github.codedoctorde.linwood.core.commands.Command;

import java.util.*;

/**
 * @author CodeDoctorDE
 */
public abstract class LinwoodModule {
    private final Set<Object> listeners = new HashSet<>();
    private final Set<Command> commands = new HashSet<>();

    public Object[] getListeners() {
        return listeners.toArray(new Object[0]);
    }

    public Object[] getCommands() {
        return commands.toArray(new Object[0]);
    }
    protected boolean registerEvents(Object... eventListeners){
        return listeners.addAll(Arrays.asList(eventListeners));
    }
    protected boolean unregisterEvents(Object... eventListeners){
        return listeners.removeAll(Arrays.asList(eventListeners));
    }
    protected boolean registerCommands(Command... registeredCommands){
        return commands.addAll(Arrays.asList(registeredCommands));
    }
    protected boolean unregisterCommands(Command... registeredCommands){
        return commands.removeAll(Arrays.asList(registeredCommands));
    }
}
