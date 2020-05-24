package com.github.codedoctorde.linwood.commands.game;

import com.github.codedoctorde.linwood.Main;
import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.entity.ServerEntity;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;

import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class StopGameCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, ServerEntity entity, String label, String[] args) {
        if(args.length > 0)
        return false;
        var bundle = getBundle(entity);
        assert bundle != null;
        if(Main.getInstance().getGameManager().getGame(entity.getServerId()) == null)
            message.getTextChannel().sendMessage(bundle.getString("NoGameRunning")).queue();
        else {
            Main.getInstance().getGameManager().stopGame(entity.getServerId());
            message.getTextChannel().sendMessage(bundle.getString("Success")).queue();
        }
        return true;
    }

    @Override
    public String[] aliases(ServerEntity entity) {
        return new String[]{
                "stop",
                "cancel",
                "stop-game",
                "s",
                "c"
        };
    }

    @Override
    public ResourceBundle getBundle(ServerEntity entity) {
        return null;
    }
}
