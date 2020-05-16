package com.github.codedoctorde.linwood.entity;

import com.ibm.icu.impl.LocaleUtility;

import javax.persistence.*;
import java.util.Locale;

/**
 * @author CodeDoctorDE
 */
@Entity
@Table(name = "servers", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID")
})
public class ServerEntity {
    @Id
    @Column(name="ID", unique = true, nullable = false)
    private Long serverId;
    private String prefix = "+lw";
    private String locale = Locale.ENGLISH.getLanguage();

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
}
