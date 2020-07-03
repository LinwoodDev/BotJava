package com.github.codedoctorde.linwood.commands.settings;

import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class LanguageCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        ResourceBundle bundle = getBundle(entity);
        assert bundle != null;
        if(args.length > 1)
            return false;
        if(args.length == 0)
            message.getChannel().sendMessage(MessageFormat.format(bundle.getString("Get"), entity.getLocalization().getDisplayName(entity.getLocalization()))).queue();
        else {
            try {
                entity.setLocale(args[0]);
                entity.save(session);
                message.getChannel().sendMessage(MessageFormat.format(bundle.getString("Set"), entity.getLocalization().getDisplayName(entity.getLocalization()))).queue();
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
                "language",
                "locale",
                "lang"
        };
    }

    @Override
    public ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.commands.settings.Language", entity.getLocalization());
    }
}
