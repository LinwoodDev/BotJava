package com.github.codedoctorde.linwood.main.commands;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;

public class ServerInfoCommand extends Command {
    @Override
    public boolean onCommand(final CommandEvent event) {
        var args = event.getArguments();
        var message = event.getMessage();
        var entity = event.getEntity();
        if(args.length == 0)
            return false;
        var guild = event.getMessage().getGuild();
        guild.findMembers(member -> !member.getUser().isBot() && member.getOnlineStatus() == OnlineStatus.ONLINE).onSuccess(onlineMembers -> guild.findMembers(member -> member.getUser().isBot()).onSuccess(bots ->
                guild.findMembers(member -> !member.getUser().isBot() && member.getOnlineStatus() != OnlineStatus.ONLINE).onSuccess(offlineMembers -> guild.retrieveBanList().queue(bans ->
                        event.reply(" ").embed(new EmbedBuilder()
                                .addField(translate(entity, "TextChannels"), String.valueOf(guild.getTextChannels().size()), true)
                                .addField(translate(entity, "VoiceChannels"), String.valueOf(guild.getVoiceChannels().size()), true)

                                .addField(translate(entity, "Roles"), String.valueOf(guild.getRoles().size()), false)
                                .addField(translate(entity, "Emotes"), String.valueOf(guild.getEmotes().size()), true)

                                .addField(translate(entity, "OnlineMembers"), String.valueOf(onlineMembers.size()), false)
                                .addField(translate(entity, "OfflineMembers"), String.valueOf(offlineMembers.size()), true)
                                .addField(translate(entity, "Bots"), String.valueOf(bots.size()), true)

                                .addField(translate(entity, "Bans"), String.valueOf(bots.size()), false)
                                .addField(translate(entity, "Boosts"), String.valueOf(guild.getBoostCount()), true)
                                .build()).queue()))));
        return true;
    }
    public ServerInfoCommand(){
        super("s-i", "s-info", "server-i", "server-info", "serveri", "serverinfo", "si", "sinfo");
    }
}
