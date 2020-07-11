package com.github.codedoctorde.linwood.listener;

import com.github.codedoctorde.linwood.Linwood;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import io.sentry.Sentry;
import net.dv8tion.jda.api.events.ShutdownEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;

/**
 * @author CodeDoctorDE
 */
public class ConnectionListener {
    public ConnectionListener(){

    }
    @SubscribeEvent
    public void onGuildJoin(GuildJoinEvent event){
        try {
            var session = Linwood.getInstance().getDatabase().getSessionFactory().openSession();
            var guild = GuildEntity.get(session, event.getGuild().getIdLong());
            guild.save(session);
            session.close();
        }
        catch(Exception e){
            e.printStackTrace();
            Sentry.capture(e);
        }
    }
    @SubscribeEvent
    public void onBotShutdown(ShutdownEvent event){
        Linwood.getInstance().getSingleApplicationManager().clearGames();
    }
}
