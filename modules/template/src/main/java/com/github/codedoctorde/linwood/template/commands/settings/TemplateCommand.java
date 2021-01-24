package com.github.codedoctorde.linwood.template.commands.settings;

import com.github.codedoctorde.linwood.core.commands.CommandManager;

/**
 * @author CodeDoctorDE
 */
public class TemplateCommand extends CommandManager {
    public TemplateCommand() {
        registerCommands(
                new CreateTemplateCommand(),
                new ListTemplateCommand(),
                new RemoveTemplateCommand()
        );
    }

}
