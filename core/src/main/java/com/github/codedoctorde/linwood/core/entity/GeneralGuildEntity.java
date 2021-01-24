package com.github.codedoctorde.linwood.core.entity;

import com.github.codedoctorde.linwood.core.Linwood;
import net.dv8tion.jda.api.entities.Role;
import org.hibernate.Session;

import javax.persistence.*;
import java.util.*;

/**
 * @author CodeDoctorDE
 */
@Entity
@Table(name = "guild")
public class GeneralGuildEntity extends GuildEntity {
    @Id
    @Column(name="id", unique = true, nullable = false)
    private long guildId;
    @ElementCollection
    @CollectionTable(name="Prefixes", joinColumns=@JoinColumn(name="guild_id"))
    @Column(name="prefix")
    private final Set<String> prefixes = new HashSet<>(Linwood.getInstance().getConfig().getPrefixes());
    private String locale = Locale.ENGLISH.toLanguageTag();
    @ElementCollection
    @CollectionTable(name="Prefixes", joinColumns=@JoinColumn(name="guild_id"))
    @Column(name="modules")
    private final Set<String> enabledModules = Set.of(Linwood.getInstance().getModulesStrings());
    private Long maintainerId = null;
    private GuildPlan plan = GuildPlan.COMMUNITY;

    public GeneralGuildEntity(){
    }
    public GeneralGuildEntity(long id) {
        this.guildId = id;
    }

    public long getGuildId() {
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


    @Deprecated
    public static GeneralGuildEntity get(Session session, long guildId){
        return Linwood.getInstance().getDatabase().getGuildById(session, guildId);
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

    public GuildPlan getPlan() {
        return plan;
    }

    public void setPlan(GuildPlan plan) {
        this.plan = plan;
    }
    public boolean addPrefix(String prefix){
        if(plan.getPrefixLimit() < 0 || plan.getPrefixLimit() > getPrefixes().size())
            return getPrefixes().add(prefix);
        return false;
    }
}
