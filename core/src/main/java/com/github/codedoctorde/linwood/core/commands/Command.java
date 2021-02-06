package com.github.codedoctorde.linwood.core.commands;

import com.github.codedoctorde.linwood.core.entity.GeneralGuildEntity;
import com.github.codedoctorde.linwood.core.exceptions.CommandPermissionException;
import com.github.codedoctorde.linwood.core.exceptions.CommandSyntaxException;
import com.github.codedoctorde.linwood.core.module.GuildOperation;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public abstract class Command implements GuildOperation {
    private final Set<String> aliases = new HashSet<>();

    protected Command(String... aliases){
        Collections.addAll(this.aliases, aliases);
    }

    abstract public void onCommand(final CommandEvent event) throws CommandSyntaxException, CommandPermissionException;
    protected ResourceBundle getBaseBundle(GeneralGuildEntity entity){
        return ResourceBundle.getBundle("locale.Command", entity.getLocalization());
    }

    public Set<String> getAliases() {
        return aliases;
    }
    public boolean hasAlias(String alias){
        return aliases.stream().anyMatch(current -> current.equalsIgnoreCase(alias));
    }
}
