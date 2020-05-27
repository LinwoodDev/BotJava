package com.github.codedoctorde.linwood.entity;

import com.github.codedoctorde.linwood.Main;
import com.ibm.icu.impl.LocaleUtility;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.hibernate.annotations.Immutable;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.Locale;
import java.util.Set;

/**
 * @author CodeDoctorDE
 */
@Entity
@Immutable
public class ServerEntity {
    @Id
    @Column(name="ID", unique = true, nullable = false)
    private long serverId;
    private String prefix = "+lw";
    private String locale = Locale.ENGLISH.getLanguage();
    @OneToMany()
    private Set<TemplateEntity> templates;
    @Column(nullable = true)
    private Long gameMasterRoleId;
    @Column(nullable = true)
    private Long gameCategoryId;

    public ServerEntity(){

    }
    public ServerEntity(String prefix, long id) {
        this.prefix = prefix;
        this.serverId = id;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Long getServerId() {
        return serverId;
    }

    public String getLocale() {
        return locale;
    }

    public Locale getLocalization(){
        return LocaleUtility.getLocaleFromName(locale);
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
    public void setGameCategory(Category category){
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
    public Role getGameMasterRole(){
        return Main.getInstance().getJda().getRoleById(gameMasterRoleId);
    }
    public void setGameMasterRole(Role role){
        gameCategoryId = role.getIdLong();
    }
}
