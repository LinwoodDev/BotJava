package com.github.codedoctorde.linwood.commands.settings.karma;

import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class LikeCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        ResourceBundle bundle = getBundle(entity);
        assert bundle != null;
        if(args.length > 1)
            return false;
        if(args.length == 0)
            if(entity.getKarmaEntity().getLikeEmote() != null)
                message.getChannel().sendMessage(MessageFormat.format(bundle.getString("Get"), entity.getKarmaEntity().getLikeEmote())).queue();
            else
                message.getChannel().sendMessage(bundle.getString("GetNull")).queue();
        else {
                entity.getKarmaEntity().setLikeEmote(args[0]);
                entity.save(session);
                message.getChannel().sendMessage(MessageFormat.format(bundle.getString("Set"), entity.getKarmaEntity().getLikeEmote())).queue();
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
                "gamecategory",
                "game-category"
        };
    }

    @Override
    public ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.commands.settings.karma.Like", entity.getLocalization());
    }
}
