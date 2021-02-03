package com.github.codedoctorde.linwood.main.commands.settings;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.core.exceptions.CommandSyntaxException;
import net.dv8tion.jda.api.Permission;

/**
 * @author CodeDoctorDE
 */
public class ClearMaintainerCommand extends Command {
    @Override
    public void onCommand(final CommandEvent event) {
        var entity = event.getEntity();
        if(event.getArguments().length != 0)
            throw new CommandSyntaxException(this);
        entity.setMaintainer(null);
        entity.save(event.getSession());
        event.reply(translate(entity, "Clear")).queue();
    }


    public ClearMaintainerCommand() {
        super("clear-control", "clear-controller", "clear-maint", "clear-maintainer", "clearcontrol", "clearcontroller", "clearmaint", "clearmaintainer");
    }
}
