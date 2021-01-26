package com.github.codedoctorde.linwood.game.commands.settings;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.game.entity.GameEntity;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;

/**
 * @author CodeDoctorDE
 */
public class GameCategoryCommand extends Command {
    @Override
    public boolean onCommand(final CommandEvent event) {
        var entity = event.getEntity();
        var args = event.getArguments();
        var gameEntity = event.getGuildEntity(GameEntity.class);
        if(args.length > 1)
            return false;
        if(args.length == 0)
            if(gameEntity.getGameCategoryId() != null)
                event.replyFormat(getTranslationString(entity, "Get"), gameEntity.getGameCategory().getName(), gameEntity.getGameCategoryId()).queue();
            else
                event.reply(getTranslationString(entity, "GetNull")).queue();
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
                        event.reply(getTranslationString(entity, "SetNothing")).queue();
                    else if(categories.size() > 1)
                        event.reply(getTranslationString(entity, "SetMultiple")).queue();
                    else
                        category = categories.get(0);
                    if(category == null)
                        return true;
                }
                gameEntity.setGameCategory(category);
                entity.save(event.getSession());
                event.replyFormat(getTranslationString(entity, "Set"), gameEntity.getGameCategory().getName(), gameEntity.getGameCategoryId()).queue();
            }catch(NullPointerException e){
                event.reply(getTranslationString(entity, "NotValid")).queue();
            }
        }
        return true;
    }
    @Override
    public boolean hasPermission(final CommandEvent event) {
       var member = event.getMember();
       var entity = event.getEntity();
       return member.hasPermission(Permission.MANAGE_SERVER) || entity.getMaintainerId() != null && member.getRoles().contains(member.getGuild().getRoleById(entity.getMaintainerId()));
    }

    public GameCategoryCommand(){
        super(
                "gamecategory",
                "game-category",
                "category"
        );
    }
}
