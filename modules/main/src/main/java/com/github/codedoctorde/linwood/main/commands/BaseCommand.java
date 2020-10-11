package com.github.codedoctorde.linwood.main.commands;

import com.github.codedoctorde.linwood.core.commands.CommandManager;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author CodeDoctorDE
 */
public class BaseCommand extends CommandManager {
    private final InfoCommand infoCommand = new InfoCommand();
    private final HelpCommand helpCommand = new HelpCommand();

    @Override
    public @NotNull Set<String> aliases(GuildEntity entity) {
        return new HashSet<>();
    }

    public void runInfo(Session session, GuildEntity entity, Message message) {
        infoCommand.onCommand(session, message, entity, "", new String[0]);
    }

    public HelpCommand getHelpCommand() {
        return helpCommand;
    }
}
