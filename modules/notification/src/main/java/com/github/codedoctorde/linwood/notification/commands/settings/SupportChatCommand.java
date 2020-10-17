package com.github.codedoctorde.linwood.notification.commands.settings;

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
public class SupportChatCommand extends Command {
    @Override
    public boolean onCommand(final CommandEvent event) {
        ResourceBundle bundle = getBundle(entity);
        if(args.length > 1)
            return false;
        if(args.length == 0)
            if(entity.getNotificationEntity().getSupportChatId() != null)
                message.getChannel().sendMessageFormat(bundle.getString("Get"), entity.getNotificationEntity().getSupportChat().getName(), entity.getNotificationEntity().getSupportChatId()).queue();
            else
                message.getChannel().sendMessage(bundle.getString("GetNull")).queue();
        else {
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
            entity.getNotificationEntity().setSupportChat(channel);
            entity.save(session);
            message.getChannel().sendMessageFormat(bundle.getString("Set"), entity.getNotificationEntity().getSupportChat().getAsMention(), entity.getNotificationEntity().getSupportChatId()).queue();
        }
        return true;
    }
    @Override
    public boolean hasPermission(final CommandEvent event) {
       var member = event.getMember();
       var entity = event.getEntity();
       return member.hasPermission(Permission.MANAGE_SERVER) || entity.getMaintainerId() != null && member.getRoles().contains(member.getGuild().getRoleById(entity.getMaintainerId()));
    }

    @Override
    public @NotNull Set<String> aliases(GuildEntity entity) {
        super(
                "supportchat",
                "support-chat",
                "support",
                "spc",
                "sp"
        );
    }
}
