package com.github.codedoctorde.linwood.main.commands.settings;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.core.exceptions.CommandSyntaxException;
import net.dv8tion.jda.api.EmbedBuilder;

/**
 * @author CodeDoctorDE
 */
public class PlanCommand extends Command {
    @Override
    public void onCommand(final CommandEvent event) {
        if(event.getArguments().length != 0)
            throw new CommandSyntaxException(this);
        var entity = event.getEntity();
        event.getMessage().getChannel().sendMessage(new EmbedBuilder()
                .addField(translate(entity, "Plan"), translate(entity, "Plan" + entity.getPlan().name()), false)
                .addField(translate(entity, "PrefixLimitTitle"), String.format(translate(entity, "PrefixLimitBody"), entity.getPlan().getPrefixLimit()), false)
                .build()).queue();
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
