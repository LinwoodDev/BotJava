package com.github.codedoctorde.linwood.commands.fun;

import com.github.codedoctorde.linwood.Linwood;
import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.ResourceBundle;

public class KarmaCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        if(args.length > 0)
        return false;
        var bundle = getBundle(entity);
        assert bundle != null;
        var memberEntity = Linwood.getInstance().getDatabase().getMemberEntity(session, Objects.requireNonNull(message.getMember()));
        message.getChannel().sendMessage(" ").embed(new EmbedBuilder()
                .setTitle(bundle.getString("Title"))
                .setDescription(bundle.getString("Body"))
                .setColor(new Color(0x3B863B))
                .setTimestamp(LocalDateTime.now())
                .setAuthor(message.getAuthor().getAsTag(), "https://discordapp.com", message.getAuthor().getAvatarUrl())
                .addField(bundle.getString("KarmaPoints"), String.valueOf(memberEntity.getKarma()), true)
                .addField(bundle.getString("Likes"), String.valueOf(memberEntity.getLikes()), true)
                .addField(bundle.getString("Dislikes"), String.valueOf(memberEntity.getDislikes()), true)
                .addField(bundle.getString("Level"), String.valueOf(memberEntity.getLevel(session)), false)
                .addField(bundle.getString("Experience"), memberEntity.getRemainingKarma(session) * 10  + "%", true)
                .addField(bundle.getString("Rank"), "invalid", true)
                .build()).queue();
        return true;
    }

    @Override
    public String[] aliases(GuildEntity entity) {
        return new String[]{
                "karma",
                "likes",
                "level",
                "levels",
                "rank"
        };
    }

    @Override
    public @Nullable ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.commands.fun.Karma", entity.getLocalization());
    }
}
