package com.github.codedoctorde.linwood.core.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "team")
public class TeamEntity extends DatabaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @OneToMany
    private final List<TeamMemberEntity> guilds = new ArrayList<>();

    public TeamEntity(){}
    public TeamEntity(GeneralGuildEntity ownerGuild, GeneralGuildEntity... memberGuilds){
        guilds.add(new TeamMemberEntity(ownerGuild, PermissionLevel.OWNER));
        Arrays.stream(memberGuilds).forEach(guild -> guilds.add(new TeamMemberEntity(guild, PermissionLevel.MEMBER)));
    }

    public List<TeamMemberEntity> getGuilds() {
        return guilds;
    }

    public Long getId() {
        return id;
    }
}