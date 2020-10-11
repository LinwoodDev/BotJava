package com.github.codedoctorde.linwood.main;

import com.github.codedoctorde.linwood.core.module.LinwoodModule;
import com.github.codedoctorde.linwood.main.commands.ClearCommand;

/**
 * @author CodeDoctorDE
 */
public class MainAddon extends LinwoodModule {
    public MainAddon() {
        super("main");
    }

    @Override
    public void onEnable() {
        registerCommands(new ClearCommand().build());
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
