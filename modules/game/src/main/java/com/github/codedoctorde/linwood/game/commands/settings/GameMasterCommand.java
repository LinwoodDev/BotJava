package com.github.codedoctorde.linwood.game.commands.settings;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.game.entity.GameEntity;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;

/**
 * @author CodeDoctorDE
 */
public class GameMasterCommand extends Command {
    @Override
    public boolean onCommand(final CommandEvent event) {
        var args = event.getArguments();
        var entity = event.getEntity();
        if(args.length > 1)
            return false;
        var gameEntity = event.getGuildEntity(GameEntity.class);
        if(args.length == 0)
            if((gameEntity.getGameCategoryId() != null))
                event.replyFormat(translate(entity, "Get"), gameEntity.getGameMasterRole().getName(), gameEntity.getGameMasterRoleId()).queue();
        else
            event.reply(translate(entity, "GetNull")).queue();
        else {
            try {
                Role role = null;
                try{
                    role = event.getMessage().getGuild().getRoleById(args[0]);
                }catch(Exception ignored){

                }
                if(role == null){
                    var roles = event.getMessage().getGuild().getRolesByName(args[0], true);
                    if(roles.size() < 1)
                        event.reply(translate(entity, "SetNothing")).queue();
                    else if(roles.size() > 1)
                        event.reply(translate(entity, "SetMultiple")).queue();
                    else
                        role = roles.get(0);
                }
                if(role == null)
                    return true;
                gameEntity.setGameMasterRole(role);
                entity.save(event.getSession());
                event.replyFormat(translate(entity, "Set"), gameEntity.getGameMasterRole().getName(), gameEntity.getGameMasterRoleId()).queue();
            }catch(NullPointerException e){
                event.reply(translate(entity, "NotValid")).queue();
            }
        }
        return true;
    }
    @Override
    public boolean hasPermission(final CommandEvent event) {
       var member = event.getMember();
       var entity = event.getEntity();
       return member.hasPermission(Permission.MANAGE_SERVER) || entity.getMaintainerId() != null && member.getRoles().contains(member.getGuild().getRoleById(entity.getMaintainerId()));
    }

    public GameMasterCommand(){
        super(
                "gamemaster",
                "game-master",
                "master"
        );
    }
}
