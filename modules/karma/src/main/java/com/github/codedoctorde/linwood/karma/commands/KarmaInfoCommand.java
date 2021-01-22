package com.github.codedoctorde.linwood.karma.commands;

import com.github.codedoctorde.linwood.core.Linwood;
import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import com.github.codedoctorde.linwood.core.utils.TagUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.*;

public class KarmaInfoCommand extends Command {
    @Override
    public boolean onCommand(final CommandEvent event) {
        var args = event.getArguments();
        var entity = event.getEntity();
        if(args.length > 1)
        return false;
        var bundle = getBundle(entity);
        if(args.length == 0)
            karmaCommand(entity, Objects.requireNonNull(event.getMember()), event.getMessage().getTextChannel());
        else{
            var action =
                    TagUtil.convertIdToMember(event.getMessage().getGuild(), args[0]);
            if(action == null){
                event.reply(bundle.getString("SetMultiple")).queue();
                return true;
            }
            action.queue(member -> {
                if(member == null) {
                    member = TagUtil.convertNameToMember(event.getMessage().getGuild(), args[0]);
                    if (member == null) {
                        event.reply(bundle.getString("SetMultiple")).queue();
                        return;
                    }
                }
                karmaCommand(entity, member, event.getMessage().getTextChannel());
            });
        }
        return true;
    }
    public void karmaCommand(GuildEntity entity, Member member, TextChannel channel){
        var bundle = getBundle(entity);
        if(member.getUser().isBot()) {
            channel.sendMessage(bundle.getString("Invalid")).queue();
            return;
        }
        var session = Linwood.getInstance().getDatabase().getSessionFactory().openSession();
        var memberEntity = Linwood.getInstance().getDatabase().getMemberEntity(session, member);
        channel.sendMessage(" ").embed(new EmbedBuilder()
                .setTitle(bundle.getString("Title"))
                .setDescription(bundle.getString("Body"))
                .setColor(new Color(0x3B863B))
                .setTimestamp(LocalDateTime.now())
                .setAuthor(member.getUser().getAsTag(), "https://discord.com", member.getUser().getAvatarUrl())
                .addField(bundle.getString("KarmaPoints"), String.valueOf(memberEntity.getKarma()), true)
                .addField(bundle.getString("Likes"), String.valueOf(memberEntity.getLikes()), true)
                .addField(bundle.getString("Dislikes"), String.valueOf(memberEntity.getDislikes()), true)
                .addField(bundle.getString("Level"), String.valueOf(memberEntity.getLevel(session)), false)
                .addField(bundle.getString("Experience"), Math.round(memberEntity.getRemainingKarma(session) * 100 * 100) / 100  + "%", true)
                .addField(bundle.getString("Rank"), "invalid", true)
                .build()).queue();
        session.close();
    }

    public KarmaInfoCommand(){
        super(
                "karma",
                "likes",
                "level",
                "levels",
                "rank",
                "info",
                "information",
                "i"
        );
    }
}
