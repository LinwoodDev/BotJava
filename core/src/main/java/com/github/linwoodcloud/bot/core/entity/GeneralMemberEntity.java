package com.github.linwoodcloud.bot.core.entity;

import com.github.linwoodcloud.bot.core.Linwood;
import org.hibernate.Session;

import javax.persistence.*;

/**
 * @author CodeDoctorDE
 */
@Entity
@Table(name = "member")
public class GeneralMemberEntity extends MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "memberId")
    private long memberId;
    @Column(name = "guildId")
    private long guildId;
    private String locale = null;

    public GeneralMemberEntity(long guildId, long memberId) {
        this.guildId = guildId;
        this.memberId = memberId;
    }
    public GeneralMemberEntity(){}

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public long getMemberId() {
        return memberId;
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
    public GeneralGuildEntity getGuild(Session session) {
        return Linwood.getInstance().getDatabase().getGuildById(session, guildId);
    }
}
