package com.github.codedoctorde.linwood.listener;

import com.github.codedoctorde.linwood.Main;
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
        var server = Main.getInstance().getDatabase().getServerById(session, event.getGuild().getIdLong());
        session.close();
    }
}
