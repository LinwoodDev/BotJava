package com.github.codedoctorde.linwood.main.commands;

import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ServerInfoCommand extends CommandImplementer {
    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        if(args.length == 0)
            return false;
        var bundle = getBundle(entity);
        var guild = message.getGuild();
        guild.findMembers(member -> !member.getUser().isBot() && member.getOnlineStatus() == OnlineStatus.ONLINE).onSuccess(onlineMembers -> guild.findMembers(member -> member.getUser().isBot()).onSuccess(bots ->
                guild.findMembers(member -> !member.getUser().isBot() && member.getOnlineStatus() != OnlineStatus.ONLINE).onSuccess(offlineMembers -> guild.retrieveBanList().queue(bans ->
                        message.getChannel().sendMessage(" ").embed(new EmbedBuilder()
                                .addField(bundle.getString("TextChannels"), String.valueOf(guild.getTextChannels().size()), true)
                                .addField(bundle.getString("VoiceChannels"), String.valueOf(guild.getVoiceChannels().size()), true)

                                .addField(bundle.getString("Roles"), String.valueOf(guild.getRoles().size()), false)
                                .addField(bundle.getString("Emotes"), String.valueOf(guild.getEmotes().size()), true)

                                .addField(bundle.getString("OnlineMembers"), String.valueOf(onlineMembers.size()), false)
                                .addField(bundle.getString("OfflineMembers"), String.valueOf(offlineMembers.size()), true)
                                .addField(bundle.getString("Bots"), String.valueOf(bots.size()), true)

                                .addField(bundle.getString("Bans"), String.valueOf(bots.size()), false)
                                .addField(bundle.getString("Boosts"), String.valueOf(guild.getBoostCount()), true)
                                .build()).queue()))));
        return true;
    }

    @Override
    public @NotNull Set<String> aliases(GuildEntity entity) {
        return new HashSet<>(Arrays.asList(
                "server-info",
                "serverinfo",
                "si",
                "s-i",
                "server-i",
                "serveri",
                "sinfo",
                "s-info"
        ));
    }
}
