package com.github.codedoctorde.linwood.main;

import com.github.codedoctorde.linwood.core.module.LinwoodModule;
import com.github.codedoctorde.linwood.main.commands.ClearCommand;
import com.github.codedoctorde.linwood.main.commands.InfoCommand;

/**
 * @author CodeDoctorDE
 */
public class MainAddon extends LinwoodModule {
    public MainAddon() {
        super("main");
    }

    @Override
    public void onEnable() {
        registerCommands(new ClearCommand(), new InfoCommand());
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
