package com.github.codedoctorde.linwood.main;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.module.LinwoodModule;
import com.github.codedoctorde.linwood.main.commands.BaseCommand;
import com.github.codedoctorde.linwood.main.commands.ClearCommand;

/**
 * @author CodeDoctorDE
 */
public class MainAddon extends LinwoodModule {
    @Override
    public void onEnable() {
        registerCommands(new ClearCommand().build());
    }

    @Override
    public void onDisable() {

    }
}
