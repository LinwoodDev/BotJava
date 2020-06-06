package com.github.codedoctorde.linwood;

import com.github.codedoctorde.linwood.commands.BaseCommand;
import com.github.codedoctorde.linwood.config.MainConfig;
import com.github.codedoctorde.linwood.game.Game;
import com.github.codedoctorde.linwood.game.GameManager;
import com.github.codedoctorde.linwood.listener.ConnectionListener;
import com.github.codedoctorde.linwood.listener.CommandListener;
import com.github.codedoctorde.linwood.server.WebInterface;
import com.github.codedoctorde.linwood.utils.ActivityChanger;
import com.github.codedoctorde.linwood.utils.DatabaseUtil;
import com.google.gson.Gson;
import io.sentry.Sentry;
import io.sentry.SentryClientFactory;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.AnnotatedEventManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ietf.jgss.GSSContext;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.net.MalformedURLException;

/**
 * @author CodeDoctorDE
 */
public class Main {
    private final Object sentry;
    private final WebInterface webInterface;
    private JDA jda;
    private final ActivityChanger activityChanger;
    private final BaseCommand baseCommand;
    private static Main instance;
    private final DatabaseUtil database;
    private final GameManager gameManager;
    private MainConfig config;
    private final File configFile = new File("./config.json");
    private static final Gson gson = new Gson();
    private static final Logger logger = LogManager.getLogger(Main.class);


    public static void main(String[] args) {
        new Main(args[0]);
    }
    public Main(String token){
        instance = this;
        Sentry.init();
        sentry = SentryClientFactory.sentryClient();
        var builder = JDABuilder.createDefault(token)
                .setEventManager(new AnnotatedEventManager())
                .addEventListeners(new CommandListener())
                .addEventListeners(new ConnectionListener());
        database = new DatabaseUtil();
        activityChanger = new ActivityChanger(Activity.listening("CodeDoctor"), Activity.watching("github/CodeDoctorDE"), Activity.playing(getVersion()), Activity.playing("games with players :D"));
        baseCommand = new BaseCommand();
        gameManager = new GameManager();

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
        try {
            jda = builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        activityChanger.start();
        webInterface = new WebInterface();
        new Thread(webInterface::start).start();
        logger.info("Successfully started the bot!");
    }

    public JDA getJda() {
        return jda;
    }

    public ActivityChanger getActivityChanger() {
        return activityChanger;
    }

    public BaseCommand getBaseCommand() {
        return baseCommand;
    }

    public static Main getInstance() {
        return instance;
    }

    public String getVersion(){
        return "Alpha-0.0.1";
    }

    public DatabaseUtil getDatabase() {
        return database;
    }

    public MainConfig getConfig() {
        return config;
    }

    public GameManager getGameManager() {
        return gameManager;
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
}
