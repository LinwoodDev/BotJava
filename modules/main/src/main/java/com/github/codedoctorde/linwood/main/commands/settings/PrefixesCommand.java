package com.github.codedoctorde.linwood.main.commands.settings;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PrefixesCommand extends Command {
    @Override
    public boolean onCommand(final CommandEvent event) {
        if(event.getArgs().length != 0)
            return false;
        var entity = event.getEntity();
        var bundle = getBundle(entity);
        event.replyFormat(bundle.getString("Get"), String.join("," , entity.getPrefixes())).queue();
        return true;
    }

    public PrefixesCommand() {
        super(
                "prefixes",
                "pre-fixes",
                "list-prefixes",
                "list-pre-fixes",
                "listprefixes",
                "listpre-fixes"
        );
    }
    @Override
    public boolean hasPermission(final CommandEvent event) {
       var member = event.getMember();
       var entity = event.getEntity();
       return member.hasPermission(Permission.MANAGE_SERVER) || entity.getMaintainerId() != null && member.getRoles().contains(member.getGuild().getRoleById(entity.getMaintainerId()));
    }
}
