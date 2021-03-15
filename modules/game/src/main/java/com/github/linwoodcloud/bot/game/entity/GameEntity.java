package com.github.linwoodcloud.bot.game.entity;

import com.github.linwoodcloud.bot.core.Linwood;
import com.github.linwoodcloud.bot.core.entity.GuildEntity;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import javax.annotation.Nullable;

public class GameEntity extends GuildEntity {
    private Long id;
    private Long gameMasterRoleId;
    private Long gameCategoryId;
    private long guildId;

    public Long getId() {
        return id;
    }

    public long getGuildId() {
        return guildId;
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
