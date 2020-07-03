package com.github.codedoctorde.linwood.commands;

import com.github.codedoctorde.linwood.Linwood;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;

import java.lang.management.ManagementFactory;
import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class InfoCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        var bundle = getBundle(entity);
        if(args.length > 0)
            return false;
        assert bundle != null;
        message.getChannel().sendMessage(" ").embed(new EmbedBuilder().setTitle(infoFormat(session, message, bundle.getString("title")))
                .setDescription(infoFormat(session, message, bundle.getString("Body.regexp"))).build()).queue();
        return true;
    }

    public String infoFormat(Session session, Message message, String text){
        var guild = GuildEntity.get(session, message.getGuild().getIdLong());
        var uptime = ManagementFactory.getRuntimeMXBean().getUptime();
        long millis = uptime % 1000;
        long second = (uptime / 1000) % 60;
        long minute = (uptime / (1000 * 60)) % 60;
        long hour = (uptime / (1000 * 60 * 60)) % 24;
        long days = (uptime / (1000 * 60 * 24));
        return MessageFormat.format(text, Linwood.getInstance().getVersion(), message.getAuthor().getAsMention(), guild.getPrefix(), days, hour, minute, second, millis, Linwood.getInstance().getConfig().getSupportURL());
    }

    @Override
    public String[] aliases(GuildEntity entity) {
        return new String[]{
                "", "info", "i", "information"
        };
    }

    @Override
    public ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.commands.Info", entity.getLocalization());
    }
}