package com.github.codedoctorde.linwood.entity;

import com.github.codedoctorde.linwood.Linwood;
import org.hibernate.Session;

import javax.persistence.*;

/**
 * @author CodeDoctorDE
 */
@Entity
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "memberId")
    private long memberId;
    @Column(name = "guildId")
    private long guildId;
    private String locale = null;
    private int points;
    private long likes = 0;
    private long dislikes = 0;

    public MemberEntity(long guildId, long memberId) {
        this.guildId = guildId;
        this.memberId = memberId;
    }
    public MemberEntity(){}

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
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

    public long getMemberId() {
        return memberId;
    }

    public int getLevel(Session session) {
        var guild = getGuild(session);
        return (int) (guild.getKarmaEntity().getConstant() * Math.sqrt(getKarma()));
    }

    public int getRemainingKarma(Session session) {
        var guild = getGuild(session);
        return (int) (guild.getKarmaEntity().getConstant() * Math.pow(getKarma(), 2) - getLevel(session));
    }

    public long getKarma() {
        return likes - dislikes;
    }

    public void save(Session session) {
        var t = session.beginTransaction();
        session.saveOrUpdate(this);
        t.commit();
    }

    public long getGuildId() {
        return guildId;
    }

    public Long getId() {
        return id;
    }
    public GuildEntity getGuild(Session session) {
        return Linwood.getInstance().getDatabase().getGuildById(session, guildId);
    }
}
