package com.github.codedoctorde.linwood.main.commands;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.core.exceptions.CommandSyntaxException;
import net.dv8tion.jda.api.Permission;

/**
 * @author CodeDoctorDE
 */
public class ClearCommand extends Command {
    @Override
    public void onCommand(final CommandEvent event) {
        var args = event.getArguments();
        var entity = event.getEntity();
        if(args.length != 1)
            throw new CommandSyntaxException(this);
        int count;
        try{
            count = Integer.parseInt(args[0]);
        }catch(Exception ignored){
            event.reply(translate(entity, "Invalid")).queue();
            return;
        }
        if(count <= 0 || count > 100)
            event.reply(translate(entity, "Between")).queue();
        else
            event.getTextChannel().getHistory().retrievePast(count).queue(messages -> {
                messages.forEach(deleteMessage -> deleteMessage.delete().queue());
                event.replyFormat(translate(entity, "Success"), messages.size()).queue();
            });
    }

    public ClearCommand(){
        super(
                "clear", "c", "clearchat","clear-chat"
        );
    }
}
