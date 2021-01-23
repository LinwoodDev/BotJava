package com.github.codedoctorde.linwood.core.entity;

import org.hibernate.Session;

public interface DatabaseEntity {
    default void save(Session session){
        var t = session.beginTransaction();
        session.saveOrUpdate(this);
        t.commit();
    }
}
