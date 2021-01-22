package com.github.codedoctorde.linwood.template.commands.settings;

import com.github.codedoctorde.linwood.core.commands.CommandManager;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Set;

/**
 * @author CodeDoctorDE
 */
public class TemplateCommand extends CommandManager {
    public TemplateCommand() {
        registerCommands(Arrays.asList(
                new CreateTemplateCommand(),
                new ListTemplateCommand(),
                new RemoveTemplateCommand()
        ));
    }

}
