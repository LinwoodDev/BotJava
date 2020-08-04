package com.github.codedoctorde.linwood.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
public class GlobalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @OneToMany
    private final List<GlobalGuildEntity> guilds = new ArrayList<>();

    public GlobalEntity(){}
    public GlobalEntity(Long ownerGuildId, Long... memberGuildIds){
        guilds.add(new GlobalGuildEntity(ownerGuildId, PermissionLevel.OWNER));
        Arrays.stream(memberGuildIds).forEach(guildId -> guilds.add(new GlobalGuildEntity(guildId, PermissionLevel.MEMBER)));
    }

    public List<GlobalGuildEntity> getGuilds() {
        return guilds;
    }

    public Long getId() {
        return id;
    }
}