package com.github.codedoctorde.linwood.fun.fun;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.*;

/**
 * @author CodeDoctorDE
 */
public class WindowsCommand extends Command {
    private final Random random = new Random();
    @Override
    public boolean onCommand(final CommandEvent event) {
        if(event.getArguments().length != 0)
            return false;
        String response;
        InputStream file = null;
        switch (random.nextInt(3)){
            case 0:
                response = getTranslationString(entity, "Crash");

                file = getClass().getClassLoader().getResourceAsStream("assets/crash.png");
                break;
            case 1:
                response = getTranslationString(entity, "Update");

                file = getClass().getClassLoader().getResourceAsStream(("assets/update.png"));
                break;
            default:
                response = getTranslationString(entity, "Loading");
                break;
        }
        var action = event.reply(response);
        if(file != null)
            action = action.addFile(file, "windows.png");
        action.queue();
        return true;
    }

    public WindowsCommand() {
        super(
                "windows",
                "win",
                "window"
        );
    }
}
