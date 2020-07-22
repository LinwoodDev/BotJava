package com.github.codedoctorde.linwood.listener;

import com.github.codedoctorde.linwood.Linwood;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import org.eclipse.jetty.util.resource.Resource;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class NotificationListener {
    @SubscribeEvent
    public void onMessageReceived(MessageReceivedEvent event){
        if(event.getAuthor().isBot())
            return;
        var session = Linwood.getInstance().getDatabase().getSessionFactory().openSession();
        var entity = Linwood.getInstance().getDatabase().getGuildById(session, event.getGuild().getIdLong());
        if(event.getChannel().getIdLong() != entity.getNotificationEntity().getSupportChatId()) return;
        var role = event.getGuild().getRoleById(entity.getNotificationEntity().getTeamRoleId());
        var bundle = getBundle(entity);
        event.getGuild().findMembers(member -> member.getRoles().contains(role) && member.getOnlineStatus() == OnlineStatus.ONLINE).onSuccess(members -> {
            if(members.size() > 0) event.getChannel().sendMessage(bundle.getString("NoSupport")).queue();
        });
    }
    @SubscribeEvent
    public void onStatus(UserUpdateOnlineStatusEvent event){
        if(event.getMember().getUser().isBot())
            return;
        var session = Linwood.getInstance().getDatabase().getSessionFactory().openSession();
        var entity = Linwood.getInstance().getDatabase().getGuildById(session, event.getGuild().getIdLong());
        var bundle = getBundle(entity);
        Role team = null;
        if(entity.getNotificationEntity().getTeamRoleId() != null)
        team = event.getGuild().getRoleById(entity.getNotificationEntity().getTeamRoleId());
        if(team == null)
            return;
        if(!event.getMember().getRoles().contains(team))
            return;
        var chat = event.getGuild().getTextChannelById(entity.getNotificationEntity().getStatusChatId());
        if(chat == null || !(event.getNewOnlineStatus() == OnlineStatus.ONLINE || event.getOldOnlineStatus() == OnlineStatus.ONLINE && event.getNewOnlineStatus() == OnlineStatus.OFFLINE))
            return;
        chat.sendMessage(" ")
                .embed(new EmbedBuilder()
                        .setTitle(statusFormat(event.getNewOnlineStatus() == OnlineStatus.ONLINE ? bundle.getString("OnlineTitle") : bundle.getString("OfflineTitle"), event.getMember()))
                        .setDescription(statusFormat(event.getNewOnlineStatus() == OnlineStatus.ONLINE ? bundle.getString("OnlineBody") : bundle.getString("OfflineBody"), event.getMember()))
                        .setAuthor(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl())
                        .setColor(new Color(0x3B863B))
                        .setTimestamp(LocalDateTime.now())
                        .setFooter(null, null)
                        .build()).queue();
    }
    public String statusFormat(String string, Member member){
        return MessageFormat.format(string, member.getAsMention(), member.getUser().getAsTag());
    }

    public @NotNull ResourceBundle getBundle(GuildEntity entity){
        return ResourceBundle.getBundle("locale.Notification", entity.getLocalization());
    }
}
