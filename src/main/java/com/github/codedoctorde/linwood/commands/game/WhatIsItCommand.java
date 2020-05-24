package com.github.codedoctorde.linwood.commands.game;

import com.github.codedoctorde.linwood.Main;
import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.entity.ServerEntity;
import com.github.codedoctorde.linwood.game.GameMode;
import com.github.codedoctorde.linwood.game.mode.whatisit.WhatIsIt;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;
import org.jetbrains.annotations.Nullable;

import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class WhatIsItCommand extends GameCommand {
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

    @Override
    protected GameMode game(Session session, Message message, ServerEntity entity) {
        return new WhatIsIt();
    }
}
