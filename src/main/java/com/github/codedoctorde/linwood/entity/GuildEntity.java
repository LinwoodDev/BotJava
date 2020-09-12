package com.github.codedoctorde.linwood.entity;

import com.github.codedoctorde.linwood.Linwood;
import com.sun.istack.NotNull;
import net.dv8tion.jda.api.entities.Role;
import org.hibernate.Session;

import javax.persistence.*;
import java.sql.SQLException;
import java.util.*;

/**
 * @author CodeDoctorDE
 */
public class GuildEntity {
    private long guildId;

    private String locale = Locale.ENGLISH.toLanguageTag();
    private int gameEntityId;
    private int karmaEntityId;
    private int notificationEntityId;
    private Long maintainerId = null;
    private GuildPlan plan = GuildPlan.COMMUNITY;

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

    public void save(){
        var connection = Linwood.getInstance().getDatabase().getConnection();

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

    public GuildPlan getPlan() {
        return plan;
    }

    public void setPlan(GuildPlan plan) {
        this.plan = plan;
    }
    public boolean addPrefix(String prefix){
        if(plan.getPrefixLimit() == -1 || plan.getPrefixLimit() <= getPrefixes().size() + 1)
            return getPrefixes().add(prefix);
        return false;
    }
    public static void setup() throws SQLException {
        var connection = Linwood.getInstance().getDatabase().getConnection();
        connection.prepareStatement("CREATE TABLE IF NOT EXISTS guilds()");
    }
    public static void get(int guildId){
        var connection = Linwood.getInstance().getDatabase().getConnection();
        var entity = new GuildEntity();
    }
}
