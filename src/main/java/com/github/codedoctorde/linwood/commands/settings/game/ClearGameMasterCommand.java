package com.github.codedoctorde.linwood.commands.settings.game;

import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;

import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class ClearGameMasterCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        ResourceBundle bundle = getBundle(entity);
        assert bundle != null;
        if(args.length != 0)
            return false;
        entity.getGameEntity().setGameMasterRole(null);
        entity.save(session);
        message.getChannel().sendMessage(bundle.getString("Clear")).queue();
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
                "cleargamemaster",
                "clear-game-master"
        };
    }

    @Override
    public ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.commands.settings.ClearGameMaster", entity.getLocalization());
    }
}
