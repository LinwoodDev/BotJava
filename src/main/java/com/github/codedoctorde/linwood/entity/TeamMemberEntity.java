package com.github.codedoctorde.linwood.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TeamMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @OneToOne
    private GuildEntity guild;
    private PermissionLevel permissionLevel;

    public TeamMemberEntity(GuildEntity guild, PermissionLevel level){
        permissionLevel = level;
        this.guild = guild;
    }

    public TeamMemberEntity() {

    }

    public PermissionLevel getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(PermissionLevel permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public Long getId() {
        return id;
    }

    public GuildEntity getGuild() {
        return guild;
    }
}
