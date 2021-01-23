package com.github.codedoctorde.linwood.template.entity;

import com.github.codedoctorde.linwood.core.entity.GuildEntity;

import javax.persistence.*;

/**
 * @author CodeDoctorDE
 */
@Entity
@Table(name = "templates", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID")
})
public class TemplateEntity {
    @Column
    @GeneratedValue
    private int id;
    @Column(name="guildID", unique = true, nullable = false)
    @Id
    private int guildId;
    @Column(name="templateName", nullable = false)
    String name;
    @Column(name="content", nullable = false, columnDefinition = "TEXT")
    String content;
    public TemplateEntity(){

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
