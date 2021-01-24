package com.github.codedoctorde.linwood.game.commands.settings;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.game.entity.GameEntity;
import net.dv8tion.jda.api.Permission;

/**
 * @author CodeDoctorDE
 */
public class ClearGameCategoryCommand extends Command {
    @Override
    public boolean onCommand(final CommandEvent event) {
        var entity = event.getGuildEntity(GameEntity.class);
        if(event.getArguments().length != 0)
            return false;
        entity.setGameCategory(null);
        entity.save(event.getSession());
        event.reply(getTranslationString(event.getEntity(), "Clear")).queue();
        return true;
    }
    @Override
    public boolean hasPermission(final CommandEvent event) {
       var member = event.getMember();
       var entity = event.getEntity();
       return member.hasPermission(Permission.MANAGE_SERVER) || entity.getMaintainerId() != null && member.getRoles().contains(member.getGuild().getRoleById(entity.getMaintainerId()));
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
