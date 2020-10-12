package com.github.codedoctorde.linwood.main.commands;

import com.github.codedoctorde.linwood.core.Linwood;
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
public class HelpCommand extends CommandImplementer {

    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        CommandImplementer commandImplementer = Linwood.getInstance().getBaseCommand().getCommand(entity, args);
        if(commandImplementer == null)
            return false;
        sendHelp(entity, commandImplementer, message.getTextChannel());
        return true;
    }

    @Override
    public @NotNull Set<String> aliases(GuildEntity entity) {
        return new HashSet<>(Arrays.asList(
                "help",
                "h"
        ));
    }

    public void sendHelp(@NotNull GuildEntity entity, @NotNull CommandImplementer commandImplementer, @NotNull TextChannel channel) {
        var commandBundle = commandImplementer.getBundle(entity);
        var output = new MessageBuilder()
                .append(" ")
                .setEmbed(new EmbedBuilder()
                        .setTitle("Help")
                        .setDescription(commandBundle.containsKey("Description")?commandBundle.getString("Description"):"")
                        .setColor(new Color(0x3B863B))
                        .setTimestamp(LocalDateTime.now())
                        .setFooter(null, null)
                        .addField("Aliases", String.join(", ", commandImplementer.aliases(entity)), true)
                        .addField("Permissions", commandBundle.containsKey("Permission")?commandBundle.getString("Permission"):"", true)
                        .addField("Syntax", commandBundle.containsKey("Syntax")?commandBundle.getString("Syntax"):"", false)
                        .build())
                .build();
        channel.sendMessage(output).queue();
    }
}
