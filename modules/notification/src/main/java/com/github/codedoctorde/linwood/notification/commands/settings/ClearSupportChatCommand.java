package com.github.codedoctorde.linwood.notification.commands.settings;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author CodeDoctorDE
 */
public class ClearSupportChatCommand extends Command {
    @Override
    public boolean onCommand(final CommandEvent event) {
        var entity = event.getEntity();
        ResourceBundle bundle = getBundle(entity);
        if(event.getArguments().length != 0)
            return false;
        entity.getNotificationEntity().setSupportChat(null);
        entity.save(event.getSession());
        event.reply(bundle.getString("Clear")).queue();
        return true;
    }
    @Override
    public boolean hasPermission(final CommandEvent event) {
       var member = event.getMember();
       var entity = event.getEntity();
       return member.hasPermission(Permission.MANAGE_SERVER) || entity.getMaintainerId() != null && member.getRoles().contains(member.getGuild().getRoleById(entity.getMaintainerId()));
    }

    public ClearSupportChatCommand() {
        super(
                "clearsupport",
                "clear-support",
                "clearsupportchat",
                "clear-supportchat",
                "clearsupport-chat",
                "clear-support-chat",
                "clearsp",
                "clear-sp",
                "clearspc",
                "clear-spc"
        );
    }
}
