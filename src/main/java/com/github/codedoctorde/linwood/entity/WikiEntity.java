package com.github.codedoctorde.linwood.entity;

import javax.persistence.*;

/**
 * @author CodeDoctorDE
 */
@Entity
@Table(name = "templates", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID")
})
public class WikiEntity {
    @Column(name="ID", unique = true, nullable = false)
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    @JoinColumn(name="server_id", nullable=false)
    private GuildEntity server;
    @Column(nullable = false)
    String name;
    @Column(name="content", nullable = false, columnDefinition = "TEXT")
    String content;
    public WikiEntity(){

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
