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
        registerCommands(new ClearCommand(), new InfoCommand(), new AddPrefixCommand(), new PrefixesCommand(), new RemovePrefixCommand(), new ServerInfoCommand(), new ClearCommand(), new ClearMaintainerCommand());
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
