package com.github.codedoctorde.linwood;

import com.github.codedoctorde.linwood.commands.BaseCommand;
import com.github.codedoctorde.linwood.listener.ConnectionListener;
import com.github.codedoctorde.linwood.listener.CommandListener;
import com.github.codedoctorde.linwood.utils.ActivityChanger;
import com.github.codedoctorde.linwood.utils.DatabaseUtil;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.AnnotatedEventManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.security.auth.login.LoginException;
import java.net.MalformedURLException;

/**
 * @author CodeDoctorDE
 */
public class Main {
    private JDA jda;
    private final ActivityChanger activityChanger;
    private final BaseCommand baseCommand;
    private static Main instance;
    private final DatabaseUtil database;
    private static final Logger logger = LogManager.getLogger(Main.class);


    public static void main(String[] args) {
        new Main(args[0]);
    }
    public Main(String token){
        instance = this;
        var builder = JDABuilder.createDefault(token)
                .setEventManager(new AnnotatedEventManager())
                .addEventListeners(new CommandListener())
                .addEventListeners(new ConnectionListener());
        database = new DatabaseUtil();
        activityChanger = new ActivityChanger(Activity.listening("CodeDoctor"), Activity.watching("CodeDoctor beim Programmieren zu"));
        baseCommand = new BaseCommand();

        try {
            jda = builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        activityChanger.start();
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
}
