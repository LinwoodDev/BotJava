package com.github.codedoctorde.linwood.game.commands;

import com.github.codedoctorde.linwood.core.Linwood;
import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.core.exceptions.CommandPermissionException;
import com.github.codedoctorde.linwood.core.exceptions.CommandSyntaxException;
import com.github.codedoctorde.linwood.game.entity.GameEntity;
import com.github.codedoctorde.linwood.game.mode.whatisit.WhatIsIt;

/**
 * @author CodeDoctorDE
 */
public class WhatIsItCommand extends Command {
    @Override
    public void onCommand(final CommandEvent event) {
        var args = event.getArguments();
        var entity = event.getEntity();
        if(args.length > 2)
            throw new CommandSyntaxException(this);
        int rounds = 5;
        assert event.getMember() != null;
        if(!event.getGuildEntity(GameEntity.class).isGameMaster(event.getMember())){
            throw new CommandPermissionException(this);
        }
        if(event.getArguments().length != 0)
            try {
                rounds = Integer.parseInt(args[0]);
            }catch(Exception e){
                event.reply(translate(entity, "NoNumber")).queue();
            }
        if(rounds > 50 || rounds < 1){
            event.reply(translate(entity, "Invalid")).queue();
        }
        Linwood.getInstance().getGameManager().startGame(event.getEntity().getGuildId(), new WhatIsIt(rounds, event.getMessage().getChannel().getIdLong()));
        event.reply(translate(entity, "Success")).queue();
    }
    public WhatIsItCommand(){
        super(
                "whatisit",
                "what-is-it",
                "what"
        );
    }
}
