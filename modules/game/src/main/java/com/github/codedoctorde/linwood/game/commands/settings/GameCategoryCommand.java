package com.github.codedoctorde.linwood.game.commands.settings;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author CodeDoctorDE
 */
public class GameCategoryCommand extends Command {
    @Override
    public boolean onCommand(final CommandEvent event) {
        var entity = event.getEntity();
        var args = event.getArguments();
        if(args.length > 1)
            return false;
        if(args.length == 0)
            if(entity.getGameEntity().getGameCategoryId() != null)
                event.replyFormat(getTranslationString(entity, "Get"), entity.getGameEntity().getGameCategory().getName(), entity.getGameEntity().getGameCategoryId()).queue();
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
                entity.getGameEntity().setGameCategory(category);
                entity.save(event.getSession());
                event.replyFormat(getTranslationString(entity, "Set"), entity.getGameEntity().getGameCategory().getName(), entity.getGameEntity().getGameCategoryId()).queue();
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
