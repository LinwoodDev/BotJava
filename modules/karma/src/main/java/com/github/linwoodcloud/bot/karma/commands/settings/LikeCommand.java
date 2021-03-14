package com.github.linwoodcloud.bot.karma.commands.settings;

import com.github.linwoodcloud.bot.core.commands.Command;
import com.github.linwoodcloud.bot.core.commands.CommandEvent;
import com.github.linwoodcloud.bot.core.exceptions.CommandSyntaxException;
import com.github.linwoodcloud.bot.karma.entity.KarmaEntity;

import java.util.*;

/**
 * @author CodeDoctorDE
 */
public class LikeCommand extends Command {
    @Override
    public void onCommand(final CommandEvent event) {
        var entity = event.getEntity();
        var args = event.getArguments();
        if(args.length > 1)
            throw new CommandSyntaxException(this);
        var karma = event.getGuildEntity(KarmaEntity.class);
        if(args.length == 0)
            if(karma.getLikeEmote() != null)
                event.replyFormat(translate(entity, "Get"), karma.getLikeEmote()).queue();
            else
                event.reply(translate(entity, "GetNull")).queue();
        else {
            var emote = args[0];
            if(Objects.equals(emote, karma.getDislikeEmote())){
                event.reply(translate(entity, "Same")).queue();
                return;
            }
            karma.setLikeEmote(args[0]);
            entity.save(event.getSession());
            event.replyFormat(translate(entity, "Set"), karma.getLikeEmote()).queue();
        }
    }


    public LikeCommand(){
        super(
                "like"
        );
    }
}
