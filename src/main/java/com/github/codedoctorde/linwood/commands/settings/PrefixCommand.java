package com.github.codedoctorde.linwood.commands.settings;

import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.entity.ServerEntity;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class PrefixCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, ServerEntity entity, String label, String[] args) {
        ResourceBundle bundle = getBundle(entity);
        if(args.length == 0)
            message.getChannel().sendMessage(MessageFormat.format(bundle.getString("get"),entity.getPrefix())).queue();
        else if(args.length == 1) {
            message.getChannel().sendMessage(MessageFormat.format(bundle.getString("set"), args[0])).queue();
        } else
            return false;
        return true;
    }

    @Override
    public String[] aliases(ServerEntity entity) {
        return new String[0];
    }

    @Override
    public ResourceBundle getBundle(ServerEntity entity) {
        return ResourceBundle.getBundle("locale.commands.settings.Prefix", entity.getLocalization());
    }
}
