package com.github.codedoctorde.linwood.listener;

import com.github.codedoctorde.linwood.Main;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;

import javax.annotation.Nonnull;
import java.util.Arrays;

/**
 * @author CodeDoctorDE
 */
public class CommandListener {
    @SubscribeEvent
    public void onCommand(@Nonnull MessageReceivedEvent event) {
        var session = Main.getInstance().getDatabase().getSessionFactory().openSession();
        var entity = Main.getInstance().getDatabase().getServerById(session, event.getGuild().getIdLong());
        if(event.getMessage().isMentioned(event.getJDA().getSelfUser())) {
            Main.getInstance().getBaseCommand().runInfo(session, entity, event.getMessage());
        }else {
            var server = Main.getInstance().getDatabase().getServerById(session, event.getGuild().getIdLong());
            var content = event.getMessage().getContentRaw();
            var prefix = server.getPrefix();
            if (content.startsWith(prefix)) {
                var command = content.substring(prefix.length()).trim().split(" ");
                if(!Main.getInstance().getBaseCommand().onCommand(session, event.getMessage(), entity, server.getPrefix(), command))
                    event.getChannel().sendMessage(Main.getInstance().getBaseCommand().syntax(entity)).queue();
            }
        }
        session.close();
    }
}
