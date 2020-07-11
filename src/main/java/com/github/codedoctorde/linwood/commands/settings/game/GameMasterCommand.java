package com.github.codedoctorde.linwood.commands.settings.game;

import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import org.hibernate.Session;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class GameMasterCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        ResourceBundle bundle = getBundle(entity);
        assert bundle != null;
        if(args.length > 1)
            return false;
        if(args.length == 0)
            if(entity.getGameEntity().getGameCategoryId() != null)
                message.getChannel().sendMessage(MessageFormat.format(bundle.getString("Get"), entity.getGameEntity().getGameCategory().getName(), entity.getGameEntity().getGameCategoryId())).queue();
            else
                message.getChannel().sendMessage(bundle.getString("GetNull")).queue();
        else {
            try {
                Role role = null;
                try{
                    role = message.getGuild().getRoleById(args[0]);
                }catch(Exception ignored){

                }
                if(role == null){
                    var roles = message.getGuild().getRolesByName(args[0], true);
                    if(roles.size() < 1)
                        message.getChannel().sendMessage(bundle.getString("SetNothing")).queue();
                    else if(roles.size() > 1)
                        message.getChannel().sendMessage(bundle.getString("SetMultiple")).queue();
                    else
                        role = roles.get(0);
                }
                if(role == null)
                    return true;
                entity.getGameEntity().setGameMasterRole(role);
                entity.save(session);
                message.getChannel().sendMessage(MessageFormat.format(bundle.getString("Set"), entity.getGameEntity().getGameMasterRole().getName(), entity.getGameEntity().getGameMasterRoleId())).queue();
            }catch(NullPointerException e){
                message.getChannel().sendMessage(bundle.getString("NotValid")).queue();
            }
        }
        return true;
    }

    @Override
    public Permission[] permissions() {
        return new Permission[]{
                Permission.MANAGE_SERVER
        };
    }

    @Override
    public String[] aliases(GuildEntity entity) {
        return new String[]{
                "gamemaster",
                "game-master"
        };
    }

    @Override
    public ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.commands.settings.GameMaster", entity.getLocalization());
    }
}
