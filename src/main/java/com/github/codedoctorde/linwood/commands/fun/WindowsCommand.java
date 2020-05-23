package com.github.codedoctorde.linwood.commands.fun;

import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.entity.ServerEntity;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class WindowsCommand implements Command {

    @Override
    public boolean onCommand(Session session, Message message, ServerEntity entity, String label, String[] args) {
        if(args.length > 0)
            return false;
        Random random = new Random();
        String response;
        File file = null;
        var bundle = getBundle(entity);
        switch (random.nextInt(3)){
            case 0:
                response = bundle.getString("Crash");

                try {
                    file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("assets/crash.png")).toURI());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                response = bundle.getString("Update");

                try {
                    file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("assets/update.png")).toURI());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                break;
            default:
                response = bundle.getString("Loading");
                break;
        }
        var action = message.getChannel().sendMessage(response);
        if(file != null)
            action = action.addFile(file);
        action.queue();
        return true;
    }

    @Override
    public String[] aliases(ServerEntity entity) {
        return new String[]{
                "windows",
                "win",
                "window"
        };
    }

    @Override
    public ResourceBundle getBundle(ServerEntity entity) {
        return ResourceBundle.getBundle("locale.commands.fun.Windows", entity.getLocalization());
    }
}
