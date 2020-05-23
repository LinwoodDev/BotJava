package com.github.codedoctorde.linwood.commands;

import com.github.codedoctorde.linwood.Main;
import com.github.codedoctorde.linwood.entity.ServerEntity;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class HelpCommand implements Command {

    @Override
    public boolean onCommand(Session session, Message message, ServerEntity entity, String label, String[] args) {
        Command command = Main.getInstance().getBaseCommand().getCommand(entity, args);
        if(command == null)
            return false;
        System.out.println("test");
        var bundle = command.getBundle(entity);
        message.getChannel().sendMessage(bundle == null || bundle.containsKey("Description")?bundle.getString("Description"): Objects.requireNonNull(getBundle(entity)).getString("Syntax")).queue();
        return true;
    }

    @Override
    public String[] aliases(ServerEntity entity) {
        return new String[]{
          "help",
          "h"
        };
    }

    @Override
    public ResourceBundle getBundle(ServerEntity entity) {
        return ResourceBundle.getBundle("locale.commands.Help", entity.getLocalization());
    }
}
