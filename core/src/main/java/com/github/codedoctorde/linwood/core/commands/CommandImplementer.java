package com.github.codedoctorde.linwood.core.commands;

import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import java.util.ResourceBundle;
import java.util.Set;

public interface CommandImplementer {
    boolean onCommand(final Session session, final Message message, final GuildEntity entity, final String label, final String[] args);
    @NotNull
    Set<String> aliases(final GuildEntity entity);
    @NotNull
    default ResourceBundle getBundle(final GuildEntity entity){
        return ResourceBundle.getBundle("locale." + getClass().getCanonicalName(), entity.getLocalization());
    }
    default boolean hasPermission(final Member member, final GuildEntity entity, final Session session){
        return true;
    }
}
