package com.github.codedoctorde.linwood.entity;

import com.github.codedoctorde.linwood.Linwood;
import net.dv8tion.jda.api.entities.Role;
import org.hibernate.Session;

import javax.persistence.*;
import java.util.*;

/**
 * @author CodeDoctorDE
 */
@Entity
public class GuildEntity {
    @Id
    @Column(name="ID", unique = true, nullable = false)
    private long guildId;
    @ElementCollection
    @CollectionTable(name="Prefixes", joinColumns=@JoinColumn(name="guild_id"))
    @Column(name="prefix")
    private final Set<String> prefixes = new HashSet<>(Linwood.getInstance().getConfig().getPrefixes());
    private String locale = Locale.ENGLISH.toLanguageTag();
    @OneToOne(cascade=CascadeType.ALL, optional = false)
    private final GameEntity gameEntity = new GameEntity();
    @OneToMany
    private Set<TemplateEntity> templates;
    @OneToOne(cascade=CascadeType.ALL, optional = false)
    private final KarmaEntity karmaEntity = new KarmaEntity();
    @OneToOne(cascade=CascadeType.ALL, optional = false)
    private final NotificationEntity notificationEntity = new NotificationEntity();
    private Long maintainerId = null;
    public enum Plan {
        DEFAULT, PRIVATE
    }
    private Plan plan = Plan.DEFAULT;

    public GuildEntity(){
    }
    public GuildEntity(long id) {
        this.guildId = id;
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

    public NotificationEntity getNotificationEntity() {
        return notificationEntity;
    }

    public Long getMaintainerId() {
        return maintainerId;
    }
    public Role getMaintainer(){
        if(maintainerId == null)
            return null;
        return Linwood.getInstance().getJda().getRoleById(maintainerId);
    }

    public void setMaintainerId(Long maintainer) {
        this.maintainerId = maintainer;
    }

    public void setMaintainer(Role role){
        if(role == null)
            maintainerId = null;
        else
            maintainerId = role.getIdLong();
    }

    public Set<String> getPrefixes() {
        return prefixes;
    }
}
