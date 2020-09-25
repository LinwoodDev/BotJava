package com.github.codedoctorde.linwood.karma.commands;

import app.Linwood;
import com.github.codedoctorde.linwood.core.commands.Command;
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

public class KarmaInfoCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        if(args.length > 1)
        return false;
        var bundle = getBundle(entity);
        if(args.length == 0)
            karmaCommand(entity, Objects.requireNonNull(message.getMember()), message.getTextChannel());
        else{
            var action =
                    TagUtil.convertIdToMember(message.getGuild(), args[0]);
            if(action == null){
                message.getChannel().sendMessage(bundle.getString("SetMultiple")).queue();
                return true;
            }
            action.queue(member -> {
                if(member == null) {
                    member = TagUtil.convertNameToMember(message.getGuild(), args[0]);
                    if (member == null) {
                        message.getChannel().sendMessage(bundle.getString("SetMultiple")).queue();
                        return;
                    }
                }
                karmaCommand(entity, member, message.getTextChannel());
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

    @Override
    public @NotNull Set<String> aliases(GuildEntity entity) {
        return new HashSet<>(Arrays.asList(
                "karma",
                "likes",
                "level",
                "levels",
                "rank",
                "info",
                "information",
                "i"
        ));
    }

    @Override
    public @NotNull ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.com.github.codedoctorde.linwood.karma.commands.karma.Info", entity.getLocalization());
    }
}
