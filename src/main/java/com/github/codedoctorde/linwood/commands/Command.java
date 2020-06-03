package com.github.codedoctorde.linwood.commands;

import com.github.codedoctorde.linwood.entity.GuildEntity;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;
import org.jetbrains.annotations.Nullable;

import java.util.ResourceBundle;

public interface Command {
    boolean onCommand(final Session session, final Message message, final GuildEntity entity, final String label, final String[] args);
    String[] aliases(final GuildEntity entity);
    @Nullable
    ResourceBundle getBundle(final GuildEntity entity);
    default Permission[] permissions(){
        return new Permission[0];
    }
}
