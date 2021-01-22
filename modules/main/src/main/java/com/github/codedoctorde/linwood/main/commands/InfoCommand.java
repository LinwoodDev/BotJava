package com.github.codedoctorde.linwood.main.commands;

import com.github.codedoctorde.linwood.core.Linwood;
import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author CodeDoctorDE
 */
public class InfoCommand extends Command {
    public InfoCommand(){
        super(
                "", "info", "i", "information"
        );
    }
    @Override
    public boolean onCommand(final CommandEvent event) {
        var bundle = getBundle(event.getEntity());
        if(event.getArguments().length != 0)
            return false;
        event.getTextChannel().sendMessage(new EmbedBuilder().setTitle(infoFormat(event.getMessage(), event.getEntity(), bundle.getString("title")))
                .setDescription(infoFormat(event.getMessage(), event.getEntity(), bundle.getString("Body.regexp"))).build()).queue();
        return true;
    }

    public String infoFormat(Message message, GuildEntity entity, String text){
        var uptime = ManagementFactory.getRuntimeMXBean().getUptime();
        long millis = uptime % 1000;
        long second = (uptime / 1000) % 60;
        long minute = (uptime / (1000 * 60)) % 60;
        long hour = (uptime / (1000 * 60 * 60)) % 24;
        long days = uptime / (1000 * 60 * 60 * 24);
        var prefixes = String.join(", ", entity.getPrefixes());
        return String.format(text, Linwood.getInstance().getVersion(), message.getAuthor().getAsMention(), prefixes.isBlank()? " ": prefixes,
                days, hour, minute, second, millis, Linwood.getInstance().getConfig().getSupportURL());
    }
}