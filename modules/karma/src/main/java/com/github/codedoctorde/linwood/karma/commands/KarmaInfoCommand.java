package com.github.codedoctorde.linwood.karma.commands;

import com.github.codedoctorde.linwood.core.Linwood;
import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.core.utils.TagUtil;
import com.github.codedoctorde.linwood.karma.entity.KarmaMemberEntity;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

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
        if(args.length == 0)
            karmaCommand(event, Objects.requireNonNull(event.getMember()));
        else{
            var action =
                    TagUtil.convertIdToMember(event.getMessage().getGuild(), args[0]);
            if(action == null){
                event.reply(getTranslationString(entity, "SetMultiple")).queue();
                return true;
            }
            action.queue(member -> {
                if(member == null) {
                    member = TagUtil.convertNameToMember(event.getMessage().getGuild(), args[0]);
                    if (member == null) {
                        event.reply(getTranslationString(entity, "SetMultiple")).queue();
                        return;
                    }
                }
                karmaCommand(event, member);
            });
        }
        return true;
    }
    public void karmaCommand(CommandEvent event, Member member){
        var entity = event.getEntity();
        if(member.getUser().isBot()) {
            event.reply(getTranslationString(entity, "Invalid")).queue();
            return;
        }
        var session = Linwood.getInstance().getDatabase().getSessionFactory().openSession();
        var karmaMemberEntity = Linwood.getInstance().getDatabase().getMemberEntity(KarmaMemberEntity.class, session, member);
        event.reply(" ").embed(new EmbedBuilder()
                .setTitle(getTranslationString(entity, "Title"))
                .setDescription(getTranslationString(entity, "Body"))
                .setColor(new Color(0x3B863B))
                .setTimestamp(LocalDateTime.now())
                .setAuthor(member.getUser().getAsTag(), "https://discord.com", member.getUser().getAvatarUrl())
                .addField(getTranslationString(entity, "KarmaPoints"), String.valueOf(karmaMemberEntity.getKarma()), true)
                .addField(getTranslationString(entity, "Likes"), String.valueOf(karmaMemberEntity.getLikes()), true)
                .addField(getTranslationString(entity, "Dislikes"), String.valueOf(karmaMemberEntity.getDislikes()), true)
                .addField(getTranslationString(entity, "Level"), String.valueOf(karmaMemberEntity.getLevel(session)), false)
                .addField(getTranslationString(entity, "Experience"), Math.round(karmaMemberEntity.getRemainingKarma(session) * 100 * 100) / 100  + "%", true)
                .addField(getTranslationString(entity, "Rank"), "invalid", true)
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
