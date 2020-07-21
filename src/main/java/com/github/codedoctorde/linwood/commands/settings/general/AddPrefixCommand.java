package com.github.codedoctorde.linwood.commands.settings.general;

import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author CodeDoctorDE
 */
public class AddPrefixCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        ResourceBundle bundle = getBundle(entity);
        if(args.length == 0)
            return false;
        else try {
            var prefix = String.join(" ", args);
            entity.getPrefixes().add(prefix);
            entity.save(session);
            message.getChannel().sendMessage(MessageFormat.format(bundle.getString("Set"), prefix)).queue();
        } catch (NullPointerException e) {
            message.getChannel().sendMessage(bundle.getString("NotValid")).queue();
        }
        return true;
    }

    @Override
    public boolean hasPermission(Member member, GuildEntity entity, Session session) {
        var maintainer = entity.getMaintainerId() == null ? null : member.getGuild().getRoleById(entity.getMaintainerId());
        return !member.hasPermission(Permission.MANAGE_SERVER) && maintainer != null && member.getRoles().contains(maintainer);
    }

    @Override
    public Set<String> aliases(GuildEntity entity) {
        return new HashSet<>(Arrays.asList(
                "prefix",
                "pre-fix"
        ));
    }

    @Override
    public @org.jetbrains.annotations.NotNull ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.commands.settings.general.Prefix", entity.getLocalization());
    }
}
