package com.github.codedoctorde.linwood.utils;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.regex.Pattern;

public final class TagUtil {
    public static TextChannel convertToTextChannel(Guild guild, String input) throws UnsupportedOperationException{
        TextChannel channel = null;
        var pattern = Pattern.compile("(<#)?(\\d+)(>)?");
        var matcher = pattern.matcher(input);
        if(matcher.find())
            try{
                channel = guild.getTextChannelById(matcher.group(2));
            }catch(Exception ignored){

            }
        if(channel == null) {
            var channels = guild.getTextChannelsByName(input, true);
            if(channels.size() == 1)
                channel = channels.get(0);
            else
                throw new UnsupportedOperationException();
        }
        return channel;
    }
    public static Role convertToRole(Guild guild, String input) throws UnsupportedOperationException{
        Role role = null;
        var pattern = Pattern.compile("(<&)?(\\d+)(>)?");
        var matcher = pattern.matcher(input);
        if(matcher.find())
            try{
                role = guild.getRoleById(matcher.group(2));
            }catch(Exception ignored){

            }
        if(role == null) {
            var roles = guild.getRolesByName(input, true);
            if(roles.size() == 1)
                role = roles.get(0);
            else
                throw new UnsupportedOperationException();
        }
        return role;
    }
    public static Member convertToMember(Guild guild, String input) throws UnsupportedOperationException{
        Member member = null;
        var pattern = Pattern.compile("(<@(!)?)?(?<id>\\d+)(>)?");
        var matcher = pattern.matcher(input);
        if(matcher.find())
            try{
                member = guild.getMemberById(matcher.group("id"));
            }catch(Exception ignored){

            }
        if(member == null) {
            var members = guild.getMembersByName(input, true);
            if(members.size() == 1)
                member = members.get(0);
            else
                throw new UnsupportedOperationException();
        }
        return member;
    }
}
