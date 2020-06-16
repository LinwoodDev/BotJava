package com.github.codedoctorde.linwood.commands.game;

import com.github.codedoctorde.linwood.Main;
import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import com.github.codedoctorde.linwood.game.GameMode;
import com.github.codedoctorde.linwood.game.mode.quizfactory.QuizFactory;
import com.github.codedoctorde.linwood.game.mode.whatisit.WhatIsIt;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class WhatIsItCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        if(args.length > 2)
            return false;
        int rounds = 5;
        var bundle = getBundle(entity);
        assert bundle != null;
        assert message.getMember() != null;
        if(!entity.isGameMaster(message.getMember())){
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
        Main.getInstance().getGameManager().startGame(entity.getGuildId(), new WhatIsIt(rounds));
        message.getTextChannel().sendMessage(bundle.getString("Success")).queue();
        return true;
    }
    @Override
    public String[] aliases(GuildEntity entity) {
        return new String[]{
                "whatisit",
                "what-is-it",
                "what"
        };
    }

    @Override
    public @Nullable ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.commands.game.WhatIsIt");
    }
}
