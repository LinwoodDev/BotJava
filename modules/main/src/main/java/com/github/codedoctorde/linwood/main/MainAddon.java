package com.github.codedoctorde.linwood.main;

import com.github.codedoctorde.linwood.core.module.LinwoodModule;
import com.github.codedoctorde.linwood.main.commands.*;

/**
 * @author CodeDoctorDE
 */
public class MainAddon extends LinwoodModule {
    private static MainAddon instance;
    public MainAddon() {
        super("main");
        instance = this;
    }

    public static MainAddon getInstance() {
        return instance;
    }

    @Override
    public void onRegister() {
        registerCommands(new AddPrefixCommand(), new ClearCommand(), new ClearMaintainerCommand(), new ModuleHelpCommand(), new InfoCommand(), new LanguageCommand(),
                new MaintainerCommand(), new PlanCommand(), new PrefixesCommand(), new RemovePrefixCommand(), new ServerInfoCommand());
        super.onRegister();
    }

    @Override
    public void onUnregister() {
        super.onUnregister();
    }
}
