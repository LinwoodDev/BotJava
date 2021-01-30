package com.github.codedoctorde.linwood.karma.commands.settings;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.core.exceptions.CommandSyntaxException;
import com.github.codedoctorde.linwood.karma.entity.KarmaEntity;
import net.dv8tion.jda.api.Permission;

import java.util.*;

/**
 * @author CodeDoctorDE
 */
public class DislikeCommand extends Command {
    @Override
    public void onCommand(final CommandEvent event) {
        var entity = event.getEntity();
        var args = event.getArguments();
        if(args.length > 1)
            throw new CommandSyntaxException(this);
        var karma = event.getGuildEntity(KarmaEntity.class);
        if(args.length == 0)
            if(karma.getDislikeEmote() != null)
                event.replyFormat(translate(entity, "Get"), karma.getDislikeEmote()).queue();
            else
                event.reply(translate(entity, "GetNull")).queue();
        else {
            var emote = args[0];
            if(Objects.equals(emote, karma.getDislikeEmote())){
                event.reply(translate(entity, "Same")).queue();
                return;
            }
            karma.setDislikeEmote(emote);
            entity.save(event.getSession());
            event.replyFormat(translate(entity, "Set"), karma.getDislikeEmote()).queue();
        }
    }

    public DislikeCommand(){
        super(
                "dislike",
                "dis-like"
        );
    }
}
