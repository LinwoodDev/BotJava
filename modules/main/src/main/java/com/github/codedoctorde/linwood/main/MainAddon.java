package com.github.codedoctorde.linwood.main;

import com.github.codedoctorde.linwood.core.module.LinwoodModule;
import com.github.codedoctorde.linwood.main.commands.*;

/**
 * @author CodeDoctorDE
 */
public class MainAddon extends LinwoodModule {
    public MainAddon() {
        super("main");
    }

    @Override
    public void onEnable() {
        registerCommands(new AddPrefixCommand(), new ClearCommand(), new ClearMaintainerCommand(), new HelpCommand(), new InfoCommand(), new LanguageCommand(), new MaintainerCommand(), new PlanCommand(), new PrefixesCommand(), new RemovePrefixCommand(), new ServerInfoCommand(), new SettingsCommand());
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
