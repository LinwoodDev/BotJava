package com.github.codedoctorde.linwood.listener;

import com.github.codedoctorde.linwood.Main;
import com.github.codedoctorde.linwood.entity.GuildEntity;
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
        var session = Main.getInstance().getDatabase().getSessionFactory().openSession();
        var guild = GuildEntity.get(session, event.getGuild().getIdLong());
        guild.save(session);
        session.close();
    }
    @SubscribeEvent
    public void onBotShutdown(ShutdownEvent event){
        Main.getInstance().getGameManager().clearGames();
    }
}
