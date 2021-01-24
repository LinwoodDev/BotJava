package com.github.codedoctorde.linwood.notification.commands.settings;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.core.utils.TagUtil;
import com.github.codedoctorde.linwood.notification.entity.NotificationEntity;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 * @author CodeDoctorDE
 */
public class SupportChatCommand extends Command {
    @Override
    public boolean onCommand(final CommandEvent event) {
        var entity = event.getEntity();
        var args = event.getArguments();
        var notificationEntity = event.getGuildEntity(NotificationEntity.class);
        if(args.length > 1)
            return false;
        if(args.length == 0)
            if(notificationEntity.getSupportChatId() != null)
                event.replyFormat(getTranslationString(entity, "Get"), notificationEntity.getSupportChat().getName(), notificationEntity.getSupportChatId()).queue();
            else
                event.reply(getTranslationString(entity, "GetNull")).queue();
        else {
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
            notificationEntity.setSupportChat(channel);
            entity.save(event.getSession());
            event.replyFormat(getTranslationString(entity, "Set"), notificationEntity.getSupportChat().getAsMention(), notificationEntity.getSupportChatId()).queue();
        }
        return true;
    }
    @Override
    public boolean hasPermission(final CommandEvent event) {
       var member = event.getMember();
       var entity = event.getEntity();
       return member.hasPermission(Permission.MANAGE_SERVER) || entity.getMaintainerId() != null && member.getRoles().contains(member.getGuild().getRoleById(entity.getMaintainerId()));
    }

    public SupportChatCommand(){
        super(
                "supportchat",
                "support-chat",
                "support",
                "spc",
                "sp"
        );
    }
}
