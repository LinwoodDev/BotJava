package com.github.codedoctorde.linwood.entity;

import com.github.codedoctorde.linwood.Main;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.GuildChannel;
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
    @OneToMany()
    private Set<TemplateEntity> templates;
    @Column(nullable = true)
    private Long gameMasterRoleId;
    @Column(nullable = true)
    private Long gameCategoryId;

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

    public Long getGameCategoryId() {
        return gameCategoryId;
    }

    public Category getGameCategory(){
        return Main.getInstance().getJda().getCategoryById(gameCategoryId);
    }

    public void setGameCategoryId(long gameCategoryId) {
        this.gameCategoryId = gameCategoryId;
    }

    public void setGameCategory(@Nullable Category category){
        if(category == null)
            gameCategoryId = null;
        else
            gameCategoryId = category.getIdLong();
    }

    public boolean isGameMaster(Member member){
        return isGameMaster(member, null);
    }
    public boolean isGameMaster(Member member, long channelId){
        return isGameMaster(member, Main.getInstance().getJda().getGuildChannelById(channelId));
    }
    public boolean isGameMaster(Member member, @Nullable GuildChannel channel){
        return member.getRoles().stream().map(Role::getIdLong).anyMatch(id -> id.equals(gameMasterRoleId)) ||
                channel == null ? member.hasPermission(Permission.MANAGE_CHANNEL) : member.hasPermission(channel, Permission.MANAGE_CHANNEL);
    }
    public void setGameMasterRole(@Nullable Role role){
        if(role == null)
            gameMasterRoleId = null;
        else
            gameMasterRoleId = role.getIdLong();
    }

    public Long getGameMasterRoleId() {
        return gameMasterRoleId;
    }
    public Role getGameMasterRole(){
        if(gameMasterRoleId == null)
            return null;
        return Main.getInstance().getJda().getRoleById(gameMasterRoleId);
    }

    public void save(Session session){
        var t = session.beginTransaction();
        session.saveOrUpdate(this);
        t.commit();
    }

    public static GuildEntity get(Session session, long guildId){
        return Main.getInstance().getDatabase().getGuildById(session, guildId);
    }
}
