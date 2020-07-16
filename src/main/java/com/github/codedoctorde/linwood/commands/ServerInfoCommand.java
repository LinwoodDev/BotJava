package com.github.codedoctorde.linwood.commands;

import com.github.codedoctorde.linwood.entity.GuildEntity;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class ServerInfoCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        if(args.length == 0)
            return false;
        message.getChannel().sendMessage(" ").embed(new EmbedBuilder()
                .build()).queue();
        return true;
    }

    @Override
    public Set<String> aliases(GuildEntity entity) {
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

    @Override
    public @Nullable ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.commands.ServerInfo", entity.getLocalization());
    }
}
