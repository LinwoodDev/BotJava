package com.github.codedoctorde.linwood.commands.settings.general;

import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;

import java.util.ResourceBundle;
import java.util.Set;

public class PrefixesCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        if(args.length == 0)
            return false;
        var bundle = getBundle(entity);
        message.getChannel().sendMessage(bundle.getString("Before")+ String.join(bundle.getString("Delimiter") + bundle.getString("After"), entity.getPrefixes())).queue();
        return true;
    }

    @Override
    public Set<String> aliases(GuildEntity entity) {
        return null;
    }

    @Override
    public @org.jetbrains.annotations.NotNull ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.commands.settings.general.prefixes", entity.getLocalization());
    }

    @Override
    public boolean hasPermission(Member member, GuildEntity entity, Session session) {
        return false;
    }
}
