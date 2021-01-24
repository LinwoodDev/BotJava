package com.github.codedoctorde.linwood.core.module;

import com.github.codedoctorde.linwood.core.Linwood;
import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.entity.DatabaseEntity;
import com.sun.istack.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * @author CodeDoctorDE
 */
public abstract class LinwoodModule {
    private final Set<Object> listeners = new HashSet<>();
    private final Set<Command> commands = new HashSet<>();
    private final Set<Class<? extends DatabaseEntity>> entities = new HashSet<>();
    private final Set<String> status = new HashSet<>();
    private final String name;
    private final Logger logger;

    protected LinwoodModule(String name) {
        this.name = name;
        logger = LogManager.getLogger(getClass());
    }

    public Set<Object> getListeners() {
        return Set.copyOf(listeners);
    }

    public Set<Command> getCommands() {
        return Set.copyOf(commands);
    }
    public Set<Class<? extends DatabaseEntity>> getEntities() {
        return Set.copyOf(entities);
    }

    protected void registerEntity(Class<? extends DatabaseEntity> entity){
        entities.add(entity);
    }
    protected void unregisterEntity(Class<? extends DatabaseEntity> entity){
        entities.remove(entity);
    }
    protected void registerEntities(Class<? extends DatabaseEntity>... current){
        Arrays.stream(current).forEach(this::registerEntity);
    }
    protected void unregisterEntities(Class<? extends DatabaseEntity>... current){
        Arrays.stream(current).forEach(this::unregisterEntity);
    }
    protected void registerEvents(Object... eventListeners){
        listeners.addAll(Arrays.asList(eventListeners));
    }
    protected void unregisterEvents(Object... eventListeners){
        listeners.removeAll(Arrays.asList(eventListeners));
    }
    protected void registerCommands(Command... registeredCommands){
        commands.addAll(Arrays.asList(registeredCommands));
    }
    protected void unregisterCommands(Command... registeredCommands){
        commands.removeAll(Arrays.asList(registeredCommands));
    }
    @Nullable
    public Command getCommand(String alias){
        return commands.stream().filter(command -> command.hasAlias(alias)).findFirst().orElse(null);
    }
    protected void clearCommands(){
        commands.clear();
    }
    public void onEnable(){
        logger.info(name + " module was enabled!");
    }
    public void onDisable(){
        clearCommands();
        logger.info(name + " module was disabled!");
    }

    public String getName() {
        return name;
    }
}
