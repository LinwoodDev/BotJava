package com.github.codedoctorde.linwood.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GlobalGuildEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private final long guildId;
    private PermissionLevel permissionLevel;
    private Long globalChat;
    private final List<Long> syncRolesId = new ArrayList<>();

    public GlobalGuildEntity(Long guildId, PermissionLevel level){
        permissionLevel = level;
        this.guildId = guildId;
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

    public long getGuildId() {
        return guildId;
    }

    public List<Long> getSyncRolesId() {
        return syncRolesId;
    }

    public Long getGlobalChat() {
        return globalChat;
    }

    public void setGlobalChat(Long globalChat) {
        this.globalChat = globalChat;
    }
}
