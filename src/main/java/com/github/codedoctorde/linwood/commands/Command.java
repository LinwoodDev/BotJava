package com.github.codedoctorde.linwood.commands;

import com.github.codedoctorde.linwood.entity.ServerEntity;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;

public interface Command {
    boolean onCommand(final Session session, final Message message, final ServerEntity entity, final String label, final String[] args);
    String[] aliases();
    String description(final ServerEntity entity);
    String syntax(final ServerEntity entity);
}
