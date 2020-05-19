package com.github.codedoctorde.linwood.commands.game.quizfactory;

import com.github.codedoctorde.linwood.Main;
import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.entity.ServerEntity;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;

/**
 * @author CodeDoctorDE
 */
public class CreateQuizFactoryCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, ServerEntity entity, String label, String[] args) {
        if(args.length <= 0)
            return false;
        return true;
    }

    @Override
    public String[] aliases(ServerEntity entity) {
        return new String[0];
    }

    @Override
    public String description(ServerEntity entity) {
        return null;
    }

    @Override
    public String syntax(ServerEntity entity) {
        return null;
    }
}
