package com.github.codedoctorde.linwood.main.commands;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
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
public class PlanCommand extends Command {
    @Override
    public boolean onCommand(final CommandEvent event) {
        if(event.getArguments().length != 0)
        return false;
        var entity = event.getEntity();
        event.getMessage().getChannel().sendMessage(new EmbedBuilder()
                .addField(getTranslationString(entity, "Plan"), getTranslationString(entity, "Plan" + entity.getPlan().name()), false)
                .addField(getTranslationString(entity, "PrefixLimitTitle"), String.format(getTranslationString(entity, "PrefixLimitBody"), entity.getPlan().getPrefixLimit()), false)
                .build()).queue();
        return true;
    }

    public PlanCommand() {
        super(
                "plan",
                "plans",
                "limit",
                "limits"
        );
    }
}
