package com.github.codedoctorde.linwood.entity;

import com.github.codedoctorde.linwood.Main;
import com.ibm.icu.impl.LocaleUtility;
import net.dv8tion.jda.api.entities.Category;
import org.hibernate.annotations.Immutable;

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
    private long gameMasterRole;
    @Column(nullable = true)
    private long gameCategoryId;

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

    public long getGameCategoryId() {
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
}
