package com.github.codedoctorde.linwood.karma.commands.settings;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.karma.entity.KarmaEntity;
import net.dv8tion.jda.api.Permission;

import java.util.*;

/**
 * @author CodeDoctorDE
 */
public class DislikeCommand extends Command {
    @Override
    public boolean onCommand(final CommandEvent event) {
        var entity = event.getEntity();
        var args = event.getArguments();
        if(args.length > 1)
            return false;
        var karma = event.getGuildEntity(KarmaEntity.class);
        if(args.length == 0)
            if(karma.getDislikeEmote() != null)
                event.replyFormat(getTranslationString(entity, "Get"), karma.getDislikeEmote()).queue();
            else
                event.reply(getTranslationString(entity, "GetNull")).queue();
        else {
            var emote = args[0];
            if(Objects.equals(emote, karma.getDislikeEmote())){
                event.reply(getTranslationString(entity, "Same")).queue();
                return true;
            }
            karma.setDislikeEmote(emote);
            entity.save(event.getSession());
            event.replyFormat(getTranslationString(entity, "Set"), karma.getDislikeEmote()).queue();
        }
        return true;
    }
    @Override
    public boolean hasPermission(final CommandEvent event) {
        var member = event.getMember();
        var entity = event.getEntity();
        return member.hasPermission(Permission.MANAGE_SERVER) || entity.getMaintainerId() != null && member.getRoles().contains(member.getGuild().getRoleById(entity.getMaintainerId()));
    }

    public DislikeCommand(){
        super(
                "dislike",
                "dis-like"
        );
    }
}
