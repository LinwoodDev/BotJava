package com.github.codedoctorde.linwood.karma.commands.settings;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author CodeDoctorDE
 */
public class LikeCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        ResourceBundle bundle = getBundle(entity);
        if(args.length > 1)
            return false;
        if(args.length == 0)
            if(entity.getKarmaEntity().getLikeEmote() != null)
                message.getChannel().sendMessageFormat(bundle.getString("Get"), entity.getKarmaEntity().getLikeEmote()).queue();
            else
                message.getChannel().sendMessage(bundle.getString("GetNull")).queue();
        else {
            var emote = args[0];
            if(Objects.equals(emote, entity.getKarmaEntity().getDislikeEmote())){
                message.getChannel().sendMessage(bundle.getString("Same")).queue();
                return true;
            }
            entity.getKarmaEntity().setLikeEmote(args[0]);
            entity.save(session);
            message.getChannel().sendMessageFormat(bundle.getString("Set"), entity.getKarmaEntity().getLikeEmote()).queue();
        }
        return true;
    }

    @Override
    public boolean hasPermission(Member member, GuildEntity entity, Session session) {
       return member.hasPermission(Permission.MANAGE_SERVER) || entity.getMaintainerId() != null && member.getRoles().contains(member.getGuild().getRoleById(entity.getMaintainerId()));
    }

    @Override
    public @NotNull Set<String> aliases(GuildEntity entity) {
        return new HashSet<>(Collections.singletonList(
                "like"
        ));
    }

    @Override
    public @NotNull ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.com.github.codedoctorde.linwood.karma.commands.settings.karma.Like", entity.getLocalization());
    }
}