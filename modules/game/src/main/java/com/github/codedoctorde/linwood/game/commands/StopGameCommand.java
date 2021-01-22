package com.github.codedoctorde.linwood.game.commands;

import com.github.codedoctorde.linwood.core.Linwood;
import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
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
    public boolean onCommand(final CommandEvent event) {
        var entity = event.getEntity();
        if(event.getArguments().length != 0)
        return false;
        var bundle = getBundle(entity);
        if(event.getMember() == null)
            return false;
        if(!entity.getGameEntity().isGameMaster(event.getMember())){
            event.reply(bundle.getString("NoPermission")).queue();
            return true;
        }
        if(Linwood.getInstance().getGameManager().getGame(entity.getGuildId()) == null)
            event.reply(bundle.getString("NoGameRunning")).queue();
        else {
            Linwood.getInstance().getGameManager().stopGame(entity.getGuildId());
            event.reply(bundle.getString("Success")).queue();
        }
        return true;
    }

    public StopGameCommand(){
        super(
                "stop",
                "stopgame",
                "cancel",
                "stop-game",
                "s",
                "c"
        );
    }
}
