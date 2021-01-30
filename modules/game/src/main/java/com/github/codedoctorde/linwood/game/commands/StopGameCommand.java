package com.github.codedoctorde.linwood.game.commands;

import com.github.codedoctorde.linwood.core.Linwood;
import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.core.exceptions.CommandPermissionException;
import com.github.codedoctorde.linwood.core.exceptions.CommandSyntaxException;
import com.github.codedoctorde.linwood.game.entity.GameEntity;

/**
 * @author CodeDoctorDE
 */
public class StopGameCommand extends Command {
    @Override
    public void onCommand(final CommandEvent event) {
        var entity = event.getEntity();
        if(event.getArguments().length != 0)
            throw new CommandSyntaxException(this);
        if(event.getMember() == null)
            throw new CommandSyntaxException(this);
        if(!event.getGuildEntity(GameEntity.class).isGameMaster(event.getMember())){
            throw new CommandPermissionException(this);
        }
        if(Linwood.getInstance().getGameManager().getGame(entity.getGuildId()) == null)
            event.reply(translate(entity, "NoGameRunning")).queue();
        else {
            Linwood.getInstance().getGameManager().stopGame(entity.getGuildId());
            event.reply(translate(entity, "Success")).queue();
        }
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
