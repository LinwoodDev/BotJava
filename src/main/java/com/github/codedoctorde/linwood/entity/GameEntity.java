package com.github.codedoctorde.linwood.entity;

import com.github.codedoctorde.linwood.Linwood;
import com.sun.istack.NotNull;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import javax.annotation.Nullable;
import javax.persistence.*;

@Entity
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column
    private Long gameMasterRoleId;
    @Column
    private Long gameCategoryId;
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "guildID", unique = true, nullable = false,referencedColumnName = "ID")
    @NotNull
    private GuildEntity guild;


    public GuildEntity getGuild() {
        return guild;
    }

    public Long getGameCategoryId() {
        return gameCategoryId;
    }

    public Category getGameCategory(){
        if(gameCategoryId == null)
            return null;
        else
            return Linwood.getInstance().getJda().getCategoryById(gameCategoryId);
    }

    public void setGameCategory(@Nullable Category category){
        if(category == null)
            gameCategoryId = null;
        else
            gameCategoryId = category.getIdLong();
    }
    public boolean isGameMaster(Member member){
        var category = getGameCategory();
        return member.getRoles().stream().map(Role::getIdLong).anyMatch(id -> id.equals(gameMasterRoleId)) ||
                (category == null ? member.hasPermission(Permission.MANAGE_CHANNEL) : member.hasPermission(category, Permission.MANAGE_CHANNEL));
    }
    public void setGameMasterRole(@Nullable Role role){
        if(role == null)
            gameMasterRoleId = null;
        else
            gameMasterRoleId = role.getIdLong();
    }

    public Long getGameMasterRoleId() {
        return gameMasterRoleId;
    }
    public Role getGameMasterRole(){
        if(gameMasterRoleId == null)
            return null;
        return Linwood.getInstance().getJda().getRoleById(gameMasterRoleId);
    }

    public void setGameCategoryId(Long gameCategoryId) {
        this.gameCategoryId = gameCategoryId;
    }

    public void setGameMasterRoleId(Long gameMasterRoleId) {
        this.gameMasterRoleId = gameMasterRoleId;
    }
}
