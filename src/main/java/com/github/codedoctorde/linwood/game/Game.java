package com.github.codedoctorde.linwood.game;

import com.github.codedoctorde.linwood.Main;
import com.github.codedoctorde.linwood.entity.ServerEntity;
import net.dv8tion.jda.api.entities.Guild;
import org.hibernate.Session;

/**
 * @author CodeDoctorDE
 */
public class Game {
    private final long serverId;
    private GameMode mode;
    private final int id;

    public Game(int id, long serverId, GameMode mode){
        this.id = id;
        this.serverId = serverId;
        this.mode = mode;
    }

    public GameMode getMode() {
        return mode;
    }

    public void setMode(GameMode mode) {
        this.mode = mode;
    }

    public long getServerId() {
        return serverId;
    }

    public Guild getGuild(){
        return Main.getInstance().getJda().getGuildById(serverId);
    }

    public ServerEntity getServerEntity(Session session){
        return Main.getInstance().getDatabase().getServerById(session, serverId);
    }
    public void stop(){
        Main.getInstance().getJda().getEventManager().unregister(this);
        mode.stop();
    }
    public void start(){
        Main.getInstance().getJda().getEventManager().register(this);
        mode.start(this);
    }

    public int getId() {
        return id;
    }
}
