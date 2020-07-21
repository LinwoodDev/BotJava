package com.github.codedoctorde.linwood.commands.settings.notification;

import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author CodeDoctorDE
 */
public class ClearStatusChatCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        ResourceBundle bundle = getBundle(entity);
        if(args.length != 0)
            return false;
        entity.getNotificationEntity().setStatusChat(null);
        entity.save(session);
        message.getChannel().sendMessage(bundle.getString("Clear")).queue();
        return true;
    }

    @Override
    public boolean hasPermission(Member member, GuildEntity entity, Session session) {
       return member.hasPermission(Permission.MANAGE_SERVER) || entity.getMaintainerId() != null && member.getRoles().contains(member.getGuild().getRoleById(entity.getMaintainerId()));
    }

    @Override
    public Set<String> aliases(GuildEntity entity) {
        return new HashSet<>(Arrays.asList(
                "clearstatus",
                "clear-status",
                "clearstatuschat",
                "clear-statuschat",
                "clearstatus-chat",
                "clear-status-chat",
                "clears",
                "clear-s",
                "clearsc",
                "clear-sc"
        ));
    }

    @Override
    public @org.jetbrains.annotations.NotNull ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.commands.settings.notification.ClearStatusChat", entity.getLocalization());
    }
}
