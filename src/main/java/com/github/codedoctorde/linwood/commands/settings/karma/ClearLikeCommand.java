package com.github.codedoctorde.linwood.commands.settings.karma;

import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author CodeDoctorDE
 */
public class ClearLikeCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        ResourceBundle bundle = getBundle(entity);
        if(args.length != 0)
            return false;
        entity.getKarmaEntity().setLikeEmote(null);
        entity.save(session);
        message.getChannel().sendMessage(bundle.getString("Clear")).queue();
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
                "clearlike",
                "clear-like"
        ));
    }

    @Override
    public @org.jetbrains.annotations.NotNull ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.commands.settings.karma.ClearLike", entity.getLocalization());
    }
}
