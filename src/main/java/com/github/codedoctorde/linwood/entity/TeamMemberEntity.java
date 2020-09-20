package com.github.codedoctorde.linwood.entity;

import javax.persistence.*;

@Entity
public class TeamMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @JoinColumn
    @OneToOne(cascade={CascadeType.ALL}, optional = false)
    private TeamEntity team;
    @OneToOne(cascade={CascadeType.ALL}, optional = false)
    private GuildEntity guild;
    private PermissionLevel level;

    public TeamMemberEntity(GuildEntity guild, TeamEntity team, PermissionLevel level){
        this.level = level;
        this.team = team;
        this.guild = guild;
    }

    public TeamMemberEntity() {

    }

    public PermissionLevel getLevel() {
        return level;
    }

    public void setLevel(PermissionLevel permissionLevel) {
        this.level = permissionLevel;
    }

    public Long getId() {
        return id;
    }

    public GuildEntity getGuild() {
        return guild;
    }

    public TeamEntity getTeam() {
        return team;
    }
}
