package com.github.codedoctorde.linwood.game.commands;

import com.github.codedoctorde.linwood.core.Linwood;
import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.game.entity.GameEntity;

/**
 * @author CodeDoctorDE
 */
public class StopGameCommand extends Command {
    @Override
    public boolean onCommand(final CommandEvent event) {
        var entity = event.getEntity();
        if(event.getArguments().length != 0)
        return false;
        if(event.getMember() == null)
            return false;
        if(!event.getGuildEntity(GameEntity.class).isGameMaster(event.getMember())){
            event.reply(translate(entity, "NoPermission")).queue();
            return true;
        }
        if(Linwood.getInstance().getGameManager().getGame(entity.getGuildId()) == null)
            event.reply(translate(entity, "NoGameRunning")).queue();
        else {
            Linwood.getInstance().getGameManager().stopGame(entity.getGuildId());
            event.reply(translate(entity, "Success")).queue();
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
