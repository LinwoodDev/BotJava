package com.github.codedoctorde.linwood.commands.settings.karma;

import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class InfoCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        return false;
    }

    @Override
    public Set<String> aliases(GuildEntity entity) {
        return new HashSet<>(Arrays.asList(
                "info",
                "i"
        ));
    }

    @Override
    public boolean hasPermission(Member member) {
        return member.hasPermission(Permission.MANAGE_SERVER);
    }

    @Override
    public @Nullable ResourceBundle getBundle(GuildEntity entity) {
        return null;
    }
}