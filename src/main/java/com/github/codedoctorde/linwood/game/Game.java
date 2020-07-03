package com.github.codedoctorde.linwood.game;

import com.github.codedoctorde.linwood.Linwood;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import net.dv8tion.jda.api.entities.Guild;
import org.hibernate.Session;

/**
 * @author CodeDoctorDE
 */
public class Game {
    private final long guildId;
    private GameMode mode;
    private final int id;

    public Game(int id, long guildId, GameMode mode){
        this.id = id;
        this.guildId = guildId;
        this.mode = mode;
    }

    public GameMode getMode() {
        return mode;
    }

    public void setMode(GameMode mode) {
        this.mode = mode;
    }

    public long getGuildId() {
        return guildId;
    }

    public Guild getGuild(){
        return Linwood.getInstance().getJda().getGuildById(guildId);
    }

    public GuildEntity getGuildEntity(Session session){
        return Linwood.getInstance().getDatabase().getGuildById(session, guildId);
    }
    public void stop(){
        Linwood.getInstance().getJda().getEventManager().unregister(this);
        mode.stop();
    }
    public void start(){
        Linwood.getInstance().getJda().getEventManager().register(this);
        mode.start(this);
    }

    public int getId() {
        return id;
    }
}
