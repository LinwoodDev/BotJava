package com.github.codedoctorde.linwood.commands;

import com.github.codedoctorde.linwood.Main;
import com.github.codedoctorde.linwood.entity.ServerEntity;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class HelpCommand implements Command {

    public ResourceBundle getBundle(ServerEntity entity){
        return ResourceBundle.getBundle("locale.commands.Info", entity.getLocalization());
    }

    @Override
    public boolean onCommand(Session session, Message message, ServerEntity entity, String label, String[] args) {
        Command command = Main.getInstance().getBaseCommand().getCommand(args);
        if(command == null)
            return false;
        var description = command.description(entity);
        if(description != null)
        message.getChannel().sendMessage(description).queue();
        return true;
    }

    @Override
    public String[] aliases() {
        return new String[]{
          "help",
          "h"
        };
    }

    @Override
    public String description(ServerEntity entity) {
        return getBundle(entity).getString("Description");
    }

    @Override
    public String syntax(ServerEntity entity) {
        return getBundle(entity).getString("Syntax");
    }
}
