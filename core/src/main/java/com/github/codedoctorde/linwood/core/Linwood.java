package com.github.codedoctorde.linwood.core;

import com.github.codedoctorde.linwood.core.apps.single.SingleApplicationManager;
import com.github.codedoctorde.linwood.core.config.MainConfig;
import com.github.codedoctorde.linwood.core.listener.ConnectionListener;
import com.github.codedoctorde.linwood.core.listener.CommandListener;
import com.github.codedoctorde.linwood.core.listener.NotificationListener;
import com.github.codedoctorde.linwood.core.module.LinwoodModule;
import com.github.codedoctorde.linwood.core.server.WebInterface;
import com.github.codedoctorde.linwood.core.utils.ActivityChanger;
import com.github.codedoctorde.linwood.core.utils.DatabaseUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.istack.Nullable;
import io.sentry.HubAdapter;
import io.sentry.Sentry;
import io.sentry.SentryClientFactory;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.AnnotatedEventManager;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author CodeDoctorDE
 */
public class Linwood {
    private final WebInterface webInterface;
    private final CommandListener commandListener;
    private JDA jda;
    private final ActivityChanger activityChanger;
    private static Linwood instance;
    private final DatabaseUtil database;
    private final SingleApplicationManager gameManager;
    private final SingleApplicationManager audioManager;
    private final List<LinwoodModule> modules = new ArrayList<>();
    private MainConfig config;
    private final File configFile = new File("./config.json");
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger logger = LogManager.getLogger(Linwood.class);


    public Linwood(String token){
        instance = this;
        Sentry.init();
        commandListener = new CommandListener();
        var builder = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES)
                .setEventManager(new AnnotatedEventManager())
                .addEventListeners(commandListener)
                .addEventListeners(new NotificationListener())
                .addEventListeners(new ConnectionListener());
        activityChanger = new ActivityChanger();
        gameManager = new SingleApplicationManager();
        audioManager = new SingleApplicationManager();

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
        configure();
        activityChanger.start();
            webInterface = new WebInterface();
            new Thread(webInterface::start).start();
        System.out.println(" _     _  _      _      ____  ____  ____ \n" +
                "/ \\   / \\/ \\  /|/ \\  /|/  _ \\/  _ \\/  _ \\\n" +
                "| |   | || |\\ ||| |  ||| / \\|| / \\|| | \\|\n" +
                "| |_/\\| || | \\||| |/\\||| \\_/|| \\_/|| |_/|\n" +
                "\\____/\\_/\\_/  \\|\\_/  \\|\\____/\\____/\\____/\n" +
                "                                         \n");
        logger.info("Successfully started the bot!");
    }

    public JDA getJda() {
        return jda;
    }

    public ActivityChanger getActivityChanger() {
        return activityChanger;
    }

    public boolean registerModules(LinwoodModule... registeredModules){
        return modules.addAll(Arrays.asList(registeredModules));
    }
    public boolean unregisterModules(LinwoodModule... registeredModules){
        return modules.removeAll(Arrays.asList(registeredModules));
    }
    public LinwoodModule[] getModules(){
        return modules.toArray(new LinwoodModule[0]);
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
        return getClass().getPackage().getImplementationVersion();
    }

    public DatabaseUtil getDatabase() {
        return database;
    }

    public MainConfig getConfig() {
        return config;
    }

    public SingleApplicationManager getGameManager() {
        return gameManager;
    }

    public SingleApplicationManager getAudioManager() {
        return audioManager;
    }

    public WebInterface getWebInterface() {
        return webInterface;
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

    public void configure(){
        activityChanger.getActivities().clear();
        config.getActivities().forEach(activityChanger.getActivities()::add);
    }

    public CommandListener getCommandListener() {
        return commandListener;
    }
}
