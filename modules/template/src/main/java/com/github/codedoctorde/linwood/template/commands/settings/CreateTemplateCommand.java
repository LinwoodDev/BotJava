package com.github.codedoctorde.linwood.template.commands.settings;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.core.exceptions.CommandSyntaxException;

/**
 * @author CodeDoctorDE
 */
public class CreateTemplateCommand extends Command {
    @Override
    public void onCommand(final CommandEvent event) {
        throw new CommandSyntaxException(this);
    }

}
