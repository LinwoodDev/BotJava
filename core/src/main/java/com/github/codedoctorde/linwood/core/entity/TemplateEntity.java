package com.github.codedoctorde.linwood.core.entity;

import javax.persistence.*;

/**
 * @author CodeDoctorDE
 */
@Entity
@Table(name = "templates", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID")
})
public class TemplateEntity {
    @Column(name="ID", unique = true, nullable = false)
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    @JoinColumn(name="server_id", nullable=false)
    private GuildEntity server;
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
