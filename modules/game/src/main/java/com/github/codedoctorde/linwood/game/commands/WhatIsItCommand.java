package com.github.codedoctorde.linwood.game.commands;

import com.github.codedoctorde.linwood.core.Linwood;
import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.game.entity.GameEntity;
import com.github.codedoctorde.linwood.game.mode.whatisit.WhatIsIt;

/**
 * @author CodeDoctorDE
 */
public class WhatIsItCommand extends Command {
    @Override
    public boolean onCommand(final CommandEvent event) {
        var args = event.getArguments();
        var entity = event.getEntity();
        if(args.length > 2)
            return false;
        int rounds = 5;
        assert event.getMember() != null;
        if(!event.getGuildEntity(GameEntity.class).isGameMaster(event.getMember())){
            event.reply(getTranslationString(entity, "NoPermission")).queue();
            return true;
        }
        if(event.getArguments().length != 0)
            try {
                rounds = Integer.parseInt(args[0]);
            }catch(Exception e){
                event.reply(getTranslationString(entity, "NoNumber")).queue();
                return true;
            }
        if(rounds > 50 || rounds < 1){
            event.reply(getTranslationString(entity, "Invalid")).queue();
            return true;
        }
        Linwood.getInstance().getGameManager().startGame(event.getEntity().getGuildId(), new WhatIsIt(rounds, event.getMessage().getChannel().getIdLong()));
        event.reply(getTranslationString(entity, "Success")).queue();
        return true;
    }
    public WhatIsItCommand(){
        super(
                "whatisit",
                "what-is-it",
                "what"
        );
    }
}
