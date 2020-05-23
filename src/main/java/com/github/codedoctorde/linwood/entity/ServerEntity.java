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
    @OneToMany(mappedBy="server")
    private Set<TemplateEntity> templates;
    private int gameCategoryId;

    public ServerEntity(){

    }
    public ServerEntity(long id) {
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
