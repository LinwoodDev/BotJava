package com.github.codedoctorde.linwood.main;

import com.github.codedoctorde.linwood.core.Linwood;
import com.github.codedoctorde.linwood.core.module.LinwoodModule;
import com.github.codedoctorde.linwood.main.commands.*;
import com.github.codedoctorde.linwood.main.commands.settings.*;
import com.github.codedoctorde.linwood.core.config.MainConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author CodeDoctorDE
 */
public class MainAddon extends LinwoodModule {
    private static MainAddon instance;
    private static final Logger logger = LogManager.getLogger(MainAddon.class);

    public MainAddon() {
        super("main", "https://linwood.tk/docs/bot/modules/main/overview");
        instance = this;
    }

    public static MainAddon getInstance() {
        return instance;
    }

    @Override
    public void onRegister() {
        registerCommands(new ClearCommand(), new CommandHelpCommand(), new InfoCommand(), new ModuleCommand(), new ServerInfoCommand(), new MemberInfoCommand());
        registerSettingsCommands(new AddPrefixCommand(), new ClearMaintainerCommand(), new LanguageCommand(), new MaintainerCommand(), new PlanCommand(), new PrefixesCommand(), new RemovePrefixCommand());
        registerActivities(Linwood.getInstance().getConfig().getActivities().toArray(String[]::new));
        super.onRegister();
    }

    @Override
    public void onUnregister() {
        super.onUnregister();
    }
}
