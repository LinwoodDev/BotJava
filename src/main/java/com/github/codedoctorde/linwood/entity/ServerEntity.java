package com.github.codedoctorde.linwood.entity;

import com.ibm.icu.impl.LocaleUtility;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
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
    private Long serverId;
    private String prefix = "+lw";
    private String locale = Locale.ENGLISH.getLanguage();
    @OneToMany()
    private Set<TemplateEntity> templates;
    @Column(nullable = true)
    private long gameMasterRole;
    @Column(nullable = true)
    private Integer gameCategoryId;

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

    public int getGameCategoryId() {
        return gameCategoryId;
    }

    public void setGameCategoryId(int gameCategoryId) {
        this.gameCategoryId = gameCategoryId;
    }
}
