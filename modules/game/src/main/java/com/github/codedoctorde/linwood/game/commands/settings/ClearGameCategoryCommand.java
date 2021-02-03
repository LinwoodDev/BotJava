package com.github.codedoctorde.linwood.game.commands.settings;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.core.exceptions.CommandSyntaxException;
import com.github.codedoctorde.linwood.game.entity.GameEntity;
import net.dv8tion.jda.api.Permission;

/**
 * @author CodeDoctorDE
 */
public class ClearGameCategoryCommand extends Command {
    @Override
    public void onCommand(final CommandEvent event) {
        var entity = event.getGuildEntity(GameEntity.class);
        if(event.getArguments().length != 0)
            throw new CommandSyntaxException(this);
        entity.setGameCategory(null);
        entity.save(event.getSession());
        event.reply(translate(event, "Clear")).queue();
    }


    public ClearGameCategoryCommand(){
        super(
                "cleargamecategory",
                "clear-game-category",
                "clearcategory",
                "clear-category"
        );
    }
}
