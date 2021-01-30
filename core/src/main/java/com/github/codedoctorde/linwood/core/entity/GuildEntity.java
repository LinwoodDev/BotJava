package com.github.codedoctorde.linwood.core.entity;

import com.github.codedoctorde.linwood.core.Linwood;
import org.hibernate.Session;

public abstract class GuildEntity extends DatabaseEntity {
    public GeneralGuildEntity getGuild(Session session){
        return Linwood.getInstance().getDatabase().getGuildById(session, getGuildId());
    }

    public abstract long getGuildId();
}
