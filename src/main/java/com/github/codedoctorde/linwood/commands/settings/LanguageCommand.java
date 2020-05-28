package com.github.codedoctorde.linwood.commands.settings;

import com.github.codedoctorde.linwood.Main;
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
            message.getChannel().sendMessage(MessageFormat.format(bundle.getString("Get"), entity.getLocalization().toLanguageTag())).queue();
        else {
            try {
                entity.setLocale(args[0]);
                Main.getInstance().getDatabase().saveEntity(session, entity);
                message.getChannel().sendMessage(MessageFormat.format(bundle.getString("Set"), args[0])).queue();
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
