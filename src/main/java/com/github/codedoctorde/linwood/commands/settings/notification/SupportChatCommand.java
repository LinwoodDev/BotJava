package com.github.codedoctorde.linwood.commands.settings.notification;

import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.hibernate.Session;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author CodeDoctorDE
 */
public class SupportChatCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        ResourceBundle bundle = getBundle(entity);
        assert bundle != null;
        if(args.length > 1)
            return false;
        if(args.length == 0)
            if(entity.getNotificationEntity().getSupportChatId() != null)
                message.getChannel().sendMessage(MessageFormat.format(bundle.getString("Get"), entity.getNotificationEntity().getSupportChat().getName(), entity.getNotificationEntity().getSupportChatId())).queue();
            else
                message.getChannel().sendMessage(bundle.getString("GetNull")).queue();
        else {
            try {
                TextChannel category = null;
                try{
                    category = message.getGuild().getTextChannelById(args[0]);
                }catch(Exception ignored){

                }
                if(category == null){
                    var categories = message.getGuild().getTextChannelsByName(args[0], true);
                    if(categories.size() < 1)
                        message.getChannel().sendMessage(bundle.getString("SetNothing")).queue();
                    else if(categories.size() > 1)
                        message.getChannel().sendMessage(bundle.getString("SetMultiple")).queue();
                    else
                        category = categories.get(0);
                    if(category == null)
                        return true;
                }
                entity.getNotificationEntity().setSupportChat(category);
                entity.save(session);
                message.getChannel().sendMessage(MessageFormat.format(bundle.getString("Set"), entity.getNotificationEntity().getSupportChat().getName(), entity.getNotificationEntity().getSupportChatId())).queue();
            }catch(NullPointerException e){
                message.getChannel().sendMessage(bundle.getString("NotValid")).queue();
            }
        }
        return true;
    }

    @Override
    public boolean hasPermission(Member member) {
        return member.hasPermission(Permission.MANAGE_SERVER);
    }

    @Override
    public Set<String> aliases(GuildEntity entity) {
        return new HashSet<>(Arrays.asList(
                "supportchat",
                "support-chat",
                "spc",
                "sp"
        ));
    }

    @Override
    public ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.commands.settings.notification.SupportChat", entity.getLocalization());
    }
}
