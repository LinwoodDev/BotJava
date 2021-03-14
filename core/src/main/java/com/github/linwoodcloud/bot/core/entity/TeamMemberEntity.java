package com.github.linwoodcloud.bot.core.entity;

import javax.persistence.*;

@Entity
@Table(name = "team_member")
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
