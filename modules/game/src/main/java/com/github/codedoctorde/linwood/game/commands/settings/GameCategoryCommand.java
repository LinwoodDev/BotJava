package com.github.codedoctorde.linwood.game.commands.settings;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.core.exceptions.CommandSyntaxException;
import com.github.codedoctorde.linwood.game.entity.GameEntity;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;

/**
 * @author CodeDoctorDE
 */
public class GameCategoryCommand extends Command {
    @Override
    public void onCommand(final CommandEvent event) {
        var entity = event.getEntity();
        var args = event.getArguments();
        var gameEntity = event.getGuildEntity(GameEntity.class);
        if(args.length > 1)
            throw new CommandSyntaxException(this);
        if(args.length == 0)
            if(gameEntity.getGameCategoryId() != null)
                event.replyFormat(translate(entity, "Get"), gameEntity.getGameCategory().getName(), gameEntity.getGameCategoryId()).queue();
            else
                event.reply(translate(entity, "GetNull")).queue();
        else {
            try {
                Category category = null;
                try{
                    category = event.getMessage().getGuild().getCategoryById(args[0]);
                }catch(Exception ignored){

                }
                if(category == null){
                    var categories = event.getMessage().getGuild().getCategoriesByName(args[0], true);
                    if(categories.size() < 1)
                        event.reply(translate(entity, "SetNothing")).queue();
                    else if(categories.size() > 1)
                        event.reply(translate(entity, "SetMultiple")).queue();
                    else
                        category = categories.get(0);
                    if(category == null)
                        return;
                }
                gameEntity.setGameCategory(category);
                entity.save(event.getSession());
                event.replyFormat(translate(entity, "Set"), gameEntity.getGameCategory().getName(), gameEntity.getGameCategoryId()).queue();
            }catch(NullPointerException e){
                event.reply(translate(entity, "NotValid")).queue();
            }
        }
    }


    public GameCategoryCommand(){
        super(
                "gamecategory",
                "game-category",
                "category"
        );
    }
}
