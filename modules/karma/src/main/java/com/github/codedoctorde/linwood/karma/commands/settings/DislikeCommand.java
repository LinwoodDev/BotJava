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
public class DislikeCommand extends Command {
    @Override
    public boolean onCommand(final CommandEvent event) {
        var entity = event.getEntity();
        var args = event.getArguments();
        ResourceBundle bundle = getBundle(entity);
        if(args.length > 1)
            return false;
        if(args.length == 0)
            if(entity.getKarmaEntity().getDislikeEmote() != null)
                event.replyFormat(bundle.getString("Get"), entity.getKarmaEntity().getDislikeEmote()).queue();
            else
                event.reply(bundle.getString("GetNull")).queue();
        else {
            var emote = args[0];
            if(Objects.equals(emote, entity.getKarmaEntity().getDislikeEmote())){
                event.reply(bundle.getString("Same")).queue();
                return true;
            }
                entity.getKarmaEntity().setDislikeEmote(emote);
                entity.save(event.getSession());
                event.replyFormat(bundle.getString("Set"), entity.getKarmaEntity().getDislikeEmote()).queue();
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
