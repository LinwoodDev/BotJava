package com.github.codedoctorde.linwood.core.entity;

import javax.persistence.*;

@Entity
public class TeamMemberEntity extends DatabaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @OneToOne
    private GeneralGuildEntity guild;
    private PermissionLevel permissionLevel;

    public TeamMemberEntity(GeneralGuildEntity guild, PermissionLevel level){
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

    public GeneralGuildEntity getGuild() {
        return guild;
    }
}
