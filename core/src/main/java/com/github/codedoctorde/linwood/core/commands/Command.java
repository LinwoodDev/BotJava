package com.github.codedoctorde.linwood.core.commands;

/**
 * @author CodeDoctorDE
 */
public class Command {
    private final CommandImplementer implementer;
    private final String[] aliases;

    public Command(CommandImplementer implementer, String... aliases){
        this.implementer = implementer;
        this.aliases = aliases;
    }

    public CommandImplementer getImplementer() {
        return implementer;
    }

    public String[] getAliases() {
        return aliases;
    }
}
