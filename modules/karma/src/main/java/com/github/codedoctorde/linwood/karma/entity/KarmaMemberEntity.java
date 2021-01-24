package com.github.codedoctorde.linwood.karma.entity;

import com.github.codedoctorde.linwood.core.Linwood;
import com.github.codedoctorde.linwood.core.entity.DatabaseEntity;
import org.hibernate.Session;

import javax.persistence.Column;
import javax.persistence.Id;

public class KarmaMemberEntity extends DatabaseEntity {
    @Id
    @Column
    private int serverId;
    @Column
    private int points;
    @Column
    private long likes = 0;
    @Column
    private long dislikes = 0;

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public long getDislikes() {
        return dislikes;
    }

    public void setDislikes(long dislikes) {
        this.dislikes = dislikes;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public KarmaEntity getKarmaEntity(Session session){
        return Linwood.getInstance().getDatabase().getEntityById(KarmaEntity.class, session, serverId);
    }

    public int getLevel(Session session) {
        return (int) (getKarmaEntity(session).getConstant() * Math.sqrt(getKarma()));
    }

    public double getRemainingKarma(Session session) {
        return getKarmaEntity(session).getConstant() * Math.sqrt(getKarma()) - getLevel(session);
    }

    public long getKarma() {
        return likes - dislikes;
    }
}
