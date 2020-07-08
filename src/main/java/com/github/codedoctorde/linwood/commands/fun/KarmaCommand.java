package com.github.codedoctorde.linwood.commands.fun;

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
        message.getChannel().sendMessage(" ").embed(new EmbedBuilder()
                .setTitle("Karma")
                .setDescription("Here you get all information to your karma statistics.")
                .setColor(new Color(0x3B863B))
                .setTimestamp(LocalDateTime.now())
                .setAuthor(message.getAuthor().getAsTag(), "https://discordapp.com", message.getAuthor().getAvatarUrl())
                .addField("Current karma points", "100", true)
                .addField("Current dislikes", "5", true)
                .addBlankField(false)
                .addField("Current level", "10", true)
                .addField("Experience", "10/20", true)
                .addField("Rank", "#1", true)
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
