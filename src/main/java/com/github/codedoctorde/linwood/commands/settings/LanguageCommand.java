package com.github.codedoctorde.linwood.commands.settings;

import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.entity.ServerEntity;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class LanguageCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, ServerEntity entity, String label, String[] args) {
        ResourceBundle bundle = getBundle(entity);
        assert bundle != null;
        if(args.length != 1)
            message.getChannel().sendMessage(MessageFormat.format(bundle.getString("get"), entity.getLocalization().toLanguageTag())).queue();
        else {
            entity.setLocale(args[0]);
            message.getChannel().sendMessage(MessageFormat.format(bundle.getString("set"), entity.getLocalization().toLanguageTag())).queue();
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
    public String[] aliases(ServerEntity entity) {
        return new String[]{
                "language",
                "locale",
                "lang"
        };
    }

    @Override
    public ResourceBundle getBundle(ServerEntity entity) {
        return ResourceBundle.getBundle("locale.commands.settings.Language", entity.getLocalization());
    }
}
