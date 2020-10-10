package com.github.codedoctorde.linwood.notification.commands.settings;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import com.github.codedoctorde.linwood.core.utils.TagUtil;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author CodeDoctorDE
 */
public class StatusChatCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        ResourceBundle bundle = getBundle(entity);
        if(args.length > 1)
            return false;
        if(args.length == 0)
            if(entity.getNotificationEntity().getStatusChatId() != null)
                message.getChannel().sendMessageFormat(bundle.getString("Get"), entity.getNotificationEntity().getStatusChat().getName(), entity.getNotificationEntity().getStatusChatId()).queue();
            else
                message.getChannel().sendMessage(bundle.getString("GetNull")).queue();
        else {
            try {
                TextChannel channel;
                try {
                    channel = TagUtil.convertToTextChannel(message.getGuild(), args[0]);
                }catch(UnsupportedOperationException ignored) {
                    message.getChannel().sendMessage(bundle.getString("SetMultiple")).queue();
                    return true;
                }
                if(channel == null) {
                    message.getChannel().sendMessage(bundle.getString("SetNothing")).queue();
                    return true;
                }
                entity.getNotificationEntity().setStatusChat(channel);
                entity.save(session);
                message.getChannel().sendMessageFormat(bundle.getString("Set"), entity.getNotificationEntity().getStatusChat().getAsMention(), entity.getNotificationEntity().getStatusChatId()).queue();
            }catch(NullPointerException e){
                message.getChannel().sendMessage(bundle.getString("NotValid")).queue();
            }
        }
        return true;
    }

    @Override
    public boolean hasPermission(Member member, GuildEntity entity, Session session) {
       return member.hasPermission(Permission.MANAGE_SERVER) || entity.getMaintainerId() != null && member.getRoles().contains(member.getGuild().getRoleById(entity.getMaintainerId()));
    }

    @Override
    public @NotNull Set<String> aliases(GuildEntity entity) {
        return new HashSet<>(Arrays.asList(
                "statuschat",
                "status-chat",
                "status",
                "sc",
                "s"
        ));
    }
}
