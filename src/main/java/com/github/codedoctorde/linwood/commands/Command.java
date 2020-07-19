package com.github.codedoctorde.linwood.commands;

import com.github.codedoctorde.linwood.entity.GuildEntity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ResourceBundle;
import java.util.Set;

public interface Command {
    boolean onCommand(final Session session, final Message message, final GuildEntity entity, final String label, final String[] args);
    Set<String> aliases(final GuildEntity entity);
    @NotNull
    ResourceBundle getBundle(final GuildEntity entity);
    default boolean hasPermission(final Member member, final GuildEntity entity, final Session session){
        return true;
    }
}
