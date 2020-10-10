package com.github.codedoctorde.linwood.core.commands;

import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author CodeDoctorDE
 */
public class PlanCommand implements CommandImplementer {
    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        if(args.length != 0)
        return false;
        var bundle = getBundle(entity);
        message.getChannel().sendMessage(new EmbedBuilder()
                .addField(bundle.getString("Plan"), bundle.getString("Plan" + entity.getPlan().name()), false)
                .addField(bundle.getString("PrefixLimitTitle"), String.format(bundle.getString("PrefixLimitBody"), entity.getPlan().getPrefixLimit()), false)
                .build()).queue();
        return true;
    }

    @Override
    public @NotNull Set<String> aliases(GuildEntity entity) {
        return new HashSet<>(Arrays.asList(
                "plan",
                "plans",
                "limit",
                "limits"
        ));
    }
}
