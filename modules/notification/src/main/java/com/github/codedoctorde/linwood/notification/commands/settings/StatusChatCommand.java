package com.github.codedoctorde.linwood.notification.commands.settings;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
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
public class StatusChatCommand extends Command {
    @Override
    public boolean onCommand(final CommandEvent event) {
        var entity = event.getEntity();
        var args = event.getArguments();
        if(args.length > 1)
            return false;
        if(args.length == 0)
            if(entity.getNotificationEntity().getStatusChatId() != null)
                event.replyFormat(getTranslationString(entity, "Get"), entity.getNotificationEntity().getStatusChat().getName(), entity.getNotificationEntity().getStatusChatId()).queue();
            else
                event.reply(getTranslationString(entity, "GetNull")).queue();
        else {
            try {
                TextChannel channel;
                try {
                    channel = TagUtil.convertToTextChannel(event.getMessage().getGuild(), args[0]);
                }catch(UnsupportedOperationException ignored) {
                    event.reply(getTranslationString(entity, "SetMultiple")).queue();
                    return true;
                }
                if(channel == null) {
                    event.reply(getTranslationString(entity, "SetNothing")).queue();
                    return true;
                }
                entity.getNotificationEntity().setStatusChat(channel);
                entity.save(event.getSession());
                event.replyFormat(getTranslationString(entity, "Set"), entity.getNotificationEntity().getStatusChat().getAsMention(), entity.getNotificationEntity().getStatusChatId()).queue();
            }catch(NullPointerException e){
                event.reply(getTranslationString(entity, "NotValid")).queue();
            }
        }
        return true;
    }
    @Override
    public boolean hasPermission(final CommandEvent event) {
       var member = event.getMember();
       var entity = event.getEntity();
       return member.hasPermission(Permission.MANAGE_SERVER) || entity.getMaintainerId() != null && member.getRoles().contains(member.getGuild().getRoleById(entity.getMaintainerId()));
    }

    public StatusChatCommand() {
        super(
                "statuschat",
                "status-chat",
                "status",
                "sc",
                "s"
        );
    }
}
