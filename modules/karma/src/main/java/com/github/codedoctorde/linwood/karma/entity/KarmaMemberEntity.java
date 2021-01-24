package com.github.codedoctorde.linwood.karma.entity;

import com.github.codedoctorde.linwood.core.Linwood;
import com.github.codedoctorde.linwood.core.entity.MemberEntity;
import org.hibernate.Session;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class KarmaMemberEntity extends MemberEntity {
    @Id
    @GeneratedValue
    @Column
    private long id;
    @Column
    private long guildId;
    @Column
    private long memberId;
    @Column
    private int points;
    @Column
    private long likes = 0;
    @Column
    private long dislikes = 0;


    public KarmaMemberEntity(long guildId, long memberId) {
        this.guildId = guildId;
        this.memberId = memberId;
    }

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
        return Linwood.getInstance().getDatabase().getGuildEntityById(KarmaEntity.class, session, guildId);
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

    @Override
    public long getGuildId() {
        return guildId;
    }

    @Override
    public long getMemberId() {
        return memberId;
    }

    public long getId() {
        return id;
    }
}
