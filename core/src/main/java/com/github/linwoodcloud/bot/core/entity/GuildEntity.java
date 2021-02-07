package com.github.linwoodcloud.bot.core.entity;

import com.github.linwoodcloud.bot.core.Linwood;
import org.hibernate.Session;

public abstract class GuildEntity extends DatabaseEntity {
    public GeneralGuildEntity getGuild(Session session){
        return Linwood.getInstance().getDatabase().getGuildById(session, getGuildId());
    }

    public abstract long getGuildId();
}
