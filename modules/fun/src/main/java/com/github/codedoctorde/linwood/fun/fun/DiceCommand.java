package com.github.codedoctorde.linwood.fun.fun;

import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author CodeDoctorDE
 */
public class DiceCommand extends Command {
    private final Random random = new Random();
    @Override
    public boolean onCommand(final CommandEvent event) {
        if(args.length > 0)
            return false;
        var bundle = getBundle(entity);
        message.getChannel().sendMessageFormat(bundle.getString("Output"), random.nextInt(5) + 1).queue();
        return true;
    }

    @Override
    public @NotNull Set<String> aliases(GuildEntity entity) {
        super(
                "dice",
                "d"
        );
    }
}
