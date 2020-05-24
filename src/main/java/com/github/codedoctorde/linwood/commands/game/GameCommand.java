package com.github.codedoctorde.linwood.commands.game;

import com.github.codedoctorde.linwood.Main;
import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.entity.ServerEntity;
import com.github.codedoctorde.linwood.game.GameMode;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;

import java.util.Objects;

/**
 * @author CodeDoctorDE
 */
public abstract class GameCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, ServerEntity entity, String label, String[] args) {
        if(args.length > 0)
            return false;
        Main.getInstance().getGameManager().startGame(message.getGuild().getIdLong(), game(session, message, entity));
        message.getTextChannel().sendMessage(Objects.requireNonNull(getBundle(entity)).getString("Success")).queue();
        return true;
    }

    protected abstract GameMode game(Session session, Message message, ServerEntity entity);
}
