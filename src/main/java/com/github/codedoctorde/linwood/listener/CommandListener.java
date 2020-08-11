package com.github.codedoctorde.linwood.listener;

import com.github.codedoctorde.linwood.Linwood;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import io.sentry.Sentry;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import org.apache.tools.ant.types.Commandline;

import javax.annotation.Nonnull;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class CommandListener {
    @SubscribeEvent
    public void onCommand(@Nonnull MessageReceivedEvent event) {
        var session = Linwood.getInstance().getDatabase().getSessionFactory().openSession();
        var guild = GuildEntity.get(session, event.getGuild().getIdLong());
        if (event.getChannelType() == ChannelType.TEXT && !event.getAuthor().isBot()) {
            var content = event.getMessage().getContentRaw();
            var prefixes = guild.getPrefixes();
            var id = event.getJDA().getSelfUser().getId();
            var nicknameMention = "<@!" + id + ">";
            var normalMention = "<@" + id + ">";
            String prefix = "";
            for (var current : prefixes)
                if (content.startsWith(current))
                    prefix = current;
            String split = null;
            if (!prefix.isBlank() && content.startsWith(prefix))
                split = prefix;
            else if (content.startsWith(nicknameMention))
                split = nicknameMention;
            else if (content.startsWith(normalMention))
                split = normalMention;
            if (split != null) {
                var command = Commandline.translateCommandline(content.substring(split.length()));
                var bundle = getBundle(guild);
                var commandBundle = Linwood.getInstance().getBaseCommand().getBundle(guild);
                try {
                    if (!Linwood.getInstance().getBaseCommand().onCommand(session, event.getMessage(), guild, prefix, command))
                        event.getChannel().sendMessage(MessageFormat.format(bundle.getString("Syntax"), commandBundle.getString("Syntax"))).queue();
                } catch (Exception e) {
                    event.getChannel().sendMessage(bundle.getString("Error")).queue();
                    e.printStackTrace();
                    Sentry.capture(e);
                }
            }
        }
        session.close();
    }

    public ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.Command", entity.getLocalization());
    }
}
