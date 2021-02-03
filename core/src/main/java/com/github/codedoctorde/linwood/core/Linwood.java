package com.github.codedoctorde.linwood.core;

import com.github.codedoctorde.linwood.core.apps.single.SingleApplicationManager;
import com.github.codedoctorde.linwood.core.config.MainConfig;
import com.github.codedoctorde.linwood.core.listener.CommandListener;
import com.github.codedoctorde.linwood.core.listener.ConnectionListener;
import com.github.codedoctorde.linwood.core.module.LinwoodModule;
import com.github.codedoctorde.linwood.core.utils.DatabaseUtil;
import com.github.codedoctorde.linwood.core.utils.LinwoodActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.istack.Nullable;
import net.dv8tion.jda.api.hooks.AnnotatedEventManager;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author CodeDoctorDE
 */
public class Linwood {
    private final CommandListener commandListener;
    private ShardManager jda;
    private final LinwoodActivity activity;
    private final File configFile = new File("./config.json");
    private MainConfig config;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static Linwood instance;
    private final DatabaseUtil database;
    private final SingleApplicationManager gameManager;
    private final SingleApplicationManager audioManager;
    private final List<LinwoodModule> modules = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(Linwood.class);


    public Linwood(String token){
        instance = this;
        //Sentry.init();
        commandListener = new CommandListener();
        var builder = DefaultShardManagerBuilder.createDefault(token)
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES)
                .setEventManagerProvider((id) -> new AnnotatedEventManager())
                .addEventListeners(commandListener)
                .addEventListeners(new ConnectionListener());
        activity = new LinwoodActivity();
        gameManager = new SingleApplicationManager();
        audioManager = new SingleApplicationManager();
        database = new DatabaseUtil();
        try {
            jda = builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            getGameManager().clearGames();
            logger.info("Shutting down...");
        }));

        // Read config file
        if(!configFile.exists()){
            try {
                if(!configFile.createNewFile())
                    logger.warn("Can't create a config file!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try{
                config = gson.fromJson(new FileReader(configFile), MainConfig.class);
            } catch (IOException e) {
                config = new MainConfig();
                e.printStackTrace();
            }
        }
        if(config == null)
            config = new MainConfig();
        saveConfig();
        System.out.println(" _     _  _      _      ____  ____  ____ \n" +
                "/ \\   / \\/ \\  /|/ \\  /|/  _ \\/  _ \\/  _ \\\n" +
                "| |   | || |\\ ||| |  ||| / \\|| / \\|| | \\|\n" +
                "| |_/\\| || | \\||| |/\\||| \\_/|| \\_/|| |_/|\n" +
                "\\____/\\_/\\_/  \\|\\_/  \\|\\____/\\____/\\____/\n" +
                "                                         \n" +
                "Version " + getVersion() + "\n");
        configure();
        logger.info("Successfully started the bot!");
    }

    public void saveConfig(){
        try {
            var writer = new FileWriter(configFile);
            gson.toJson(config, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MainConfig getConfig() {
        return config;
    }

    public ShardManager getJda() {
        return jda;
    }

    public LinwoodActivity getActivity() {
        return activity;
    }

    public boolean registerModules(LinwoodModule... registeredModules){
        var array = Arrays.asList(registeredModules);
        array.forEach(LinwoodModule::onRegister);
        return modules.addAll(array);
    }
    public boolean unregisterModules(LinwoodModule... registeredModules){
        var array = Arrays.asList(registeredModules);
        array.forEach(LinwoodModule::onUnregister);
        return modules.removeAll(array);
    }
    public LinwoodModule[] getModules(){
        return modules.toArray(new LinwoodModule[0]);
    }
    public String[] getModulesStrings(){
        return modules.stream().map(LinwoodModule::getName).toArray(String[]::new);
    }
    public String getModulesString(){
        return String.join(" ", getModulesStrings());
    }
    @Nullable
    public <T extends LinwoodModule> T getModule(Class<T> moduleClass){
        return modules.stream().filter(moduleClass::isInstance).findFirst().map(moduleClass::cast).orElse(null);
    }
    public LinwoodModule getModule(String module){
        return getModule(module, LinwoodModule.class);
    }

    @Nullable
    public <T extends LinwoodModule> T getModule(String module, Class<T> moduleClass){
        return modules.stream().filter(current -> current.getName().equalsIgnoreCase(module)).findFirst().map(moduleClass::cast).orElse(null);
    }

    public static Linwood getInstance() {
        return instance;
    }

    public String getVersion(){
        var implementation = getClass().getPackage().getImplementationVersion();
        return implementation == null ? "Snapshot" : implementation;
    }

    public DatabaseUtil getDatabase() {
        return database;
    }

    public SingleApplicationManager getGameManager() {
        return gameManager;
    }

    public SingleApplicationManager getAudioManager() {
        return audioManager;
    }

    public void configure(){
        modules.forEach(LinwoodModule::onRegister);
        activity.updateStatus();
    }

    public CommandListener getCommandListener() {
        return commandListener;
    }
}
