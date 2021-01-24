package com.github.codedoctorde.linwood.main;

import com.github.codedoctorde.linwood.core.entity.GeneralMemberEntity;
import com.github.codedoctorde.linwood.core.module.LinwoodModule;
import com.github.codedoctorde.linwood.main.commands.*;
import org.hibernate.Session;

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
    public void onEnable() {
        registerCommands(new AddPrefixCommand(), new ClearCommand(), new ClearMaintainerCommand(), new HelpCommand(), new InfoCommand(), new LanguageCommand(),
                new MaintainerCommand(), new PlanCommand(), new PrefixesCommand(), new RemovePrefixCommand(), new ServerInfoCommand());
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
