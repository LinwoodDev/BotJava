package com.github.codedoctorde.linwood.console;

import com.github.codedoctorde.linwood.entity.ServerEntity;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;

/**
 * @author CodeDoctorDE
 */
public interface ConsoleCommand {
    boolean onCommand(final String label, final String[] args);
    String[] aliases();
    String description();
    String syntax();
}
