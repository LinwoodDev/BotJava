package com.github.codedoctorde.linwood.commands.settings.general;

import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class RemovePrefixCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        if(args.length != 1)
        return false;
        var bundle = getBundle(entity);
        int index;
        try {
            index = Integer.parseInt(args[0]);
        }catch(Exception e){
            message.getChannel().sendMessage(bundle.getString("Invalid")).queue();
            return true;
        }
        if(index < 0 || index >= entity.getPrefixes().size()){
            message.getChannel().sendMessage(bundle.getString("Invalid")).queue();
            return true;
        }
        entity.getPrefixes().remove(index);
        message.getChannel().sendMessage(bundle.getString("Success")).queue();
        return true;
    }

    @Override
    public Set<String> aliases(GuildEntity entity) {
        return new HashSet<>(Arrays.asList(
                "remove-prefix",
                "removeprefix"
        ));
    }

    @Override
    public @NotNull ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.commands.settings.general.RemovePrefix", entity.getLocalization());
    }
}
