package com.github.codedoctorde.linwood.commands.fun;

import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;

import java.text.MessageFormat;
import java.util.*;

/**
 * @author CodeDoctorDE
 */
public class DiceCommand implements Command {
    private final Random random = new Random();

    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        if(args.length > 0)
            return false;
        var bundle = getBundle(entity);
        message.getChannel().sendMessage(MessageFormat.format(bundle.getString("Output"), random.nextInt(5) + 1)).queue();
        return true;
    }

    @Override
    public Set<String> aliases(GuildEntity entity) {
        return new HashSet<>(Arrays.asList(
                "dice",
                "d"
        ));
    }

    @Override
    public @org.jetbrains.annotations.NotNull ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.commands.fun.Dice", entity.getLocalization());
    }
}
