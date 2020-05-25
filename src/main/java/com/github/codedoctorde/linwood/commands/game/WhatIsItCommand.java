package com.github.codedoctorde.linwood.commands.game;

import com.github.codedoctorde.linwood.Main;
import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.entity.ServerEntity;
import com.github.codedoctorde.linwood.game.GameMode;
import com.github.codedoctorde.linwood.game.mode.quizfactory.QuizFactory;
import com.github.codedoctorde.linwood.game.mode.whatisit.WhatIsIt;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class WhatIsItCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, ServerEntity entity, String label, String[] args) {
        if(args.length < 2)
            return false;
        int rounds;
        var bundle = getBundle(entity);
        assert bundle != null;
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
        Main.getInstance().getGameManager().startGame(entity.getServerId(), new QuizFactory(message.getAuthor().getIdLong()));
        message.getTextChannel().sendMessage(bundle.getString("Success")).queue();
        return true;
    }
    @Override
    public String[] aliases(ServerEntity entity) {
        return new String[]{
                "whatisit",
                "what-is-it",
                "what"
        };
    }

    @Override
    public @Nullable ResourceBundle getBundle(ServerEntity entity) {
        return ResourceBundle.getBundle("locale.commands.game.WhatIsIt");
    }
}
