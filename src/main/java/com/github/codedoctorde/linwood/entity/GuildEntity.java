package com.github.codedoctorde.linwood.entity;

import com.github.codedoctorde.linwood.Linwood;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.hibernate.Session;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.Locale;
import java.util.Set;

/**
 * @author CodeDoctorDE
 */
@Entity
public class GuildEntity {
    @Id
    @Column(name="ID", unique = true, nullable = false)
    private long guildId;
    private String prefix = "+lw";
    private String locale = Locale.ENGLISH.toLanguageTag();
    @OneToOne(cascade=CascadeType.ALL, optional = false)
    private final GameEntity gameEntity = new GameEntity();
    @OneToMany
    private Set<TemplateEntity> templates;
    @OneToOne(cascade=CascadeType.ALL, optional = false)
    private final KarmaEntity karmaEntity = new KarmaEntity();

    public GuildEntity(){
    }
    public GuildEntity(String prefix, long id) {
        this.prefix = prefix;
        this.guildId = id;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Long getGuildId() {
        return guildId;
    }

    public String getLocale() {
        return locale;
    }

    public Locale getLocalization(){
        return Locale.forLanguageTag(locale);
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void save(Session session){
        var t = session.beginTransaction();
        session.saveOrUpdate(this);
        t.commit();
    }

    public static GuildEntity get(Session session, long guildId){
        return Linwood.getInstance().getDatabase().getGuildById(session, guildId);
    }

    public GameEntity getGameEntity() {
        return gameEntity;
    }

    public KarmaEntity getKarmaEntity() {
        return karmaEntity;
    }
}
