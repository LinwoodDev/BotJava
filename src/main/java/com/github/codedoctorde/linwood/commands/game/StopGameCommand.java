package com.github.codedoctorde.linwood.commands.game;

import com.github.codedoctorde.linwood.Linwood;
import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;

import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class StopGameCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        if(args.length > 0)
        return false;
        var bundle = getBundle(entity);
        assert bundle != null;
        if(message.getMember() == null)
            return false;
        if(!entity.getGameEntity().isGameMaster(message.getMember())){
            message.getChannel().sendMessage(bundle.getString("NoPermission")).queue();
            return true;
        }
        if(Linwood.getInstance().getGameManager().getGame(entity.getGuildId()) == null)
            message.getTextChannel().sendMessage(bundle.getString("NoGameRunning")).queue();
        else {
            Linwood.getInstance().getGameManager().stopGame(entity.getGuildId());
            message.getTextChannel().sendMessage(bundle.getString("Success")).queue();
        }
        return true;
    }

    @Override
    public String[] aliases(GuildEntity entity) {
        return new String[]{
                "stop",
                "stopgame",
                "cancel",
                "stop-game",
                "s",
                "c"
        };
    }

    @Override
    public ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.commands.game.Stop");
    }
}
