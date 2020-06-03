package com.github.codedoctorde.linwood.commands.settings;

import com.github.codedoctorde.linwood.Main;
import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class PrefixCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        ResourceBundle bundle = getBundle(entity);
        assert bundle != null;
        if(args.length == 0)
            message.getChannel().sendMessage(MessageFormat.format(bundle.getString("Get"), entity.getPrefix())).queue();
        else {
            entity.setPrefix(String.join(" ", Arrays.asList(args)));
            Main.getInstance().getDatabase().updateEntity(session, entity);
            message.getChannel().sendMessage(MessageFormat.format(bundle.getString("Set"), entity.getPrefix())).queue();
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
                "prefix",
                "pre-fix"
        };
    }

    @Override
    public ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.commands.settings.Prefix", entity.getLocalization());
    }
}
