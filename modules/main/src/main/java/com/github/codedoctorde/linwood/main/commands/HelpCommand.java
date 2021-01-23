package com.github.codedoctorde.linwood.main.commands;

import com.github.codedoctorde.linwood.core.Linwood;
import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author CodeDoctorDE
 */
public class HelpCommand extends Command {
    @Override
    public boolean onCommand(final CommandEvent event) {
        if(event.getArguments().length == 0)
            return false;
        var command = Linwood.getInstance().getCommandListener().findCommand(event.getArgumentsString());

        return true;
    }

    public HelpCommand() {
        super(
                "help",
                "h"
        );
    }
}
