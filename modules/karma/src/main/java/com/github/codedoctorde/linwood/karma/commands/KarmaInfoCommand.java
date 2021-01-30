package com.github.codedoctorde.linwood.karma.commands;

import com.github.codedoctorde.linwood.core.Linwood;
import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.core.exceptions.CommandSyntaxException;
import com.github.codedoctorde.linwood.core.utils.TagUtil;
import com.github.codedoctorde.linwood.karma.entity.KarmaMemberEntity;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.*;

public class KarmaInfoCommand extends Command {
    @Override
    public void onCommand(final CommandEvent event) {
        var args = event.getArguments();
        var entity = event.getEntity();
        if(args.length > 1)
        throw new CommandSyntaxException(this);
        if(args.length == 0)
            karmaCommand(event, Objects.requireNonNull(event.getMember()));
        else{
            var action =
                    TagUtil.convertIdToMember(event.getMessage().getGuild(), args[0]);
            if(action == null){
                event.reply(translate(entity, "SetMultiple")).queue();
                return;
            }
            action.queue(member -> {
                if(member == null) {
                    member = TagUtil.convertNameToMember(event.getMessage().getGuild(), args[0]);
                    if (member == null) {
                        event.reply(translate(entity, "SetMultiple")).queue();
                        return;
                    }
                }
                karmaCommand(event, member);
            });
        }
    }
    public void karmaCommand(CommandEvent event, Member member){
        var entity = event.getEntity();
        if(member.getUser().isBot()) {
            event.reply(translate(entity, "Invalid")).queue();
            return;
        }
        var session = Linwood.getInstance().getDatabase().getSessionFactory().openSession();
        var karmaMemberEntity = Linwood.getInstance().getDatabase().getMember(KarmaMemberEntity.class, session, member);
        event.reply(" ").embed(new EmbedBuilder()
                .setTitle(translate(entity, "Title"))
                .setDescription(translate(entity, "Body"))
                .setColor(new Color(0x3B863B))
                .setTimestamp(LocalDateTime.now())
                .setAuthor(member.getUser().getAsTag(), "https://discord.com", member.getUser().getAvatarUrl())
                .addField(translate(entity, "KarmaPoints"), String.valueOf(karmaMemberEntity.getKarma()), true)
                .addField(translate(entity, "Likes"), String.valueOf(karmaMemberEntity.getLikes()), true)
                .addField(translate(entity, "Dislikes"), String.valueOf(karmaMemberEntity.getDislikes()), true)
                .addField(translate(entity, "Level"), String.valueOf(karmaMemberEntity.getLevel(session)), false)
                .addField(translate(entity, "Experience"), Math.round(karmaMemberEntity.getRemainingKarma(session) * 100 * 100) / 100  + "%", true)
                .addField(translate(entity, "Rank"), "invalid", true)
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
