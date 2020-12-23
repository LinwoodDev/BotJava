package com.github.codedoctorde.linwood.core.module;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.sun.istack.Nullable;

import java.util.*;

/**
 * @author CodeDoctorDE
 */
public abstract class LinwoodModule {
    private final Set<Object> listeners = new HashSet<>();
    private final Set<Command> commands = new HashSet<>();
    private final Set<String> status = new HashSet<>();
    private final String name;

    protected LinwoodModule(String name) {
        this.name = name;
    }

    public Set<Object> getListeners() {
        return Set.copyOf(listeners);
    }

    public Set<Command> getCommands() {
        return Set.copyOf(commands);
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
    @Nullable
    public Command getCommand(String alias){
        return commands.stream().filter(command -> command.hasAlias(alias)).findFirst().orElse(null);
    }
    protected void clearCommands(){
        commands.clear();
    }
    public void onEnable(){}
    public void onDisable(){
        clearCommands();
    }

    public String getName() {
        return name;
    }
}