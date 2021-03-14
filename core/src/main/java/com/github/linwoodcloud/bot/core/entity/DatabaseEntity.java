package com.github.linwoodcloud.bot.core.entity;

import org.hibernate.Session;

public abstract class DatabaseEntity {
    public void save(Session session){
        final var t = session.beginTransaction();
        session.saveOrUpdate(this);
        t.commit();
    }
}
