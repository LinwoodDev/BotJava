package com.github.codedoctorde.linwood.game.commands;

import com.github.codedoctorde.linwood.core.Linwood;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author CodeDoctorDE
 */
public class StopGameCommand extends Command {
    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        if(args.length > 0)
        return false;
        var bundle = getBundle(entity);
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
    public @NotNull Set<String> aliases(GuildEntity entity) {
        return new HashSet<>(Arrays.asList(
                "stop",
                "stopgame",
                "cancel",
                "stop-game",
                "s",
                "c"
        ));
    }
}
