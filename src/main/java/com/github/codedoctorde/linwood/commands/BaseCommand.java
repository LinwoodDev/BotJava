package com.github.codedoctorde.linwood.commands;

import com.github.codedoctorde.linwood.commands.fun.FunCommand;
import com.github.codedoctorde.linwood.commands.game.GameCommand;
import com.github.codedoctorde.linwood.commands.karma.KarmaCommand;
import com.github.codedoctorde.linwood.commands.settings.SettingsCommand;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author CodeDoctorDE
 */
public class BaseCommand extends CommandManager {
    private final InfoCommand infoCommand = new InfoCommand();
    private final HelpCommand helpCommand = new HelpCommand();
    @Override
    public Command[] commands() {
        return new Command[]{
                new KarmaCommand(),
                new HelpCommand(),
                new FunCommand(),
                new GameCommand(),
                new SettingsCommand(),
                infoCommand
        };
    }

    @Override
    public @NotNull Set<String> aliases(GuildEntity entity) {
        return new HashSet<>();
    }

    @Override
    public @NotNull ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.commands.Base", entity.getLocalization());
    }

    public void runInfo(Session session, GuildEntity entity, Message message) {
        infoCommand.onCommand(session, message, entity, "", new String[0]);
    }

    public HelpCommand getHelpCommand() {
        return helpCommand;
    }
}
