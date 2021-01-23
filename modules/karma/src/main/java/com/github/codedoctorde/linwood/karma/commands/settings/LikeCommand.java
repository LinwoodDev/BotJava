package com.github.codedoctorde.linwood.karma.commands.settings;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
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
public class LikeCommand extends Command {
    @Override
    public boolean onCommand(final CommandEvent event) {
        var entity = event.getEntity();
        var args = event.getArguments();
        if(args.length > 1)
            return false;
        if(args.length == 0)
            if(entity.getKarmaEntity().getLikeEmote() != null)
                event.replyFormat(getTranslationString(entity, "Get"), entity.getKarmaEntity().getLikeEmote()).queue();
            else
                event.reply(getTranslationString(entity, "GetNull")).queue();
        else {
            var emote = args[0];
            if(Objects.equals(emote, entity.getKarmaEntity().getDislikeEmote())){
                event.reply(getTranslationString(entity, "Same")).queue();
                return true;
            }
            entity.getKarmaEntity().setLikeEmote(args[0]);
            entity.save(event.getSession());
            event.replyFormat(getTranslationString(entity, "Set"), entity.getKarmaEntity().getLikeEmote()).queue();
        }
        return true;
    }
    @Override
    public boolean hasPermission(final CommandEvent event) {
       var member = event.getMember();
       var entity = event.getEntity();
       return member.hasPermission(Permission.MANAGE_SERVER) || entity.getMaintainerId() != null && member.getRoles().contains(member.getGuild().getRoleById(entity.getMaintainerId()));
    }

    public LikeCommand(){
        super(
                "like"
        );
    }
}
