package com.github.codedoctorde.linwood.game.commands;

import com.github.codedoctorde.linwood.core.Linwood;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import com.github.codedoctorde.linwood.game.mode.whatisit.WhatIsIt;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author CodeDoctorDE
 */
public class WhatIsItCommand extends Command {
    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        if(args.length > 2)
            return false;
        int rounds = 5;
        var bundle = getBundle(entity);
        assert message.getMember() != null;
        if(!entity.getGameEntity().isGameMaster(message.getMember())){
            message.getChannel().sendMessage(bundle.getString("NoPermission")).queue();
            return true;
        }
        if(args.length != 0)
            try {
                rounds = Integer.parseInt(args[0]);
            }catch(Exception e){
                message.getTextChannel().sendMessage(bundle.getString("NoNumber")).queue();
                return true;
            }
        if(rounds > 50 || rounds < 1){
            message.getTextChannel().sendMessage(bundle.getString("Invalid")).queue();
            return true;
        }
        Linwood.getInstance().getGameManager().startGame(entity.getGuildId(), new WhatIsIt(rounds, message.getChannel().getIdLong()));
        message.getTextChannel().sendMessage(bundle.getString("Success")).queue();
        return true;
    }
    @Override
    public @NotNull Set<String> aliases(GuildEntity entity) {
        return new HashSet<>(Arrays.asList(
                "whatisit",
                "what-is-it",
                "what"
        ));
    }
}
