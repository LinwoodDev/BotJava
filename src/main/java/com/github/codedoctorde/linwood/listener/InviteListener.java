package com.github.codedoctorde.linwood.listener;

import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.invite.GenericGuildInviteEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;

/**
 * @author CodeDoctorDE
 */
public class InviteListener {
    @SubscribeEvent
    public void onInvite(GenericGuildInviteEvent event){
    }
}
