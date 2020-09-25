package com.github.codedoctorde.linwood.core.commands;

import app.Linwood;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author CodeDoctorDE
 */
public class InfoCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        var bundle = getBundle(entity);
        if(args.length > 0)
            return false;
        message.getChannel().sendMessage(new EmbedBuilder().setTitle(infoFormat(message, entity, bundle.getString("title")))
                .setDescription(infoFormat(message, entity, bundle.getString("Body.regexp"))).build()).queue();
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

    @Override
    public @NotNull Set<String> aliases(GuildEntity entity) {
        return new HashSet<>(Arrays.asList(
                "", "info", "i", "information"
        ));
    }

    @Override
    public @NotNull ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.com.github.codedoctorde.linwood.karma.commands.Info", entity.getLocalization());
    }
}