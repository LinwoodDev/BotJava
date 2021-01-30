package com.github.codedoctorde.linwood.core.exceptions;

import com.github.codedoctorde.linwood.core.commands.Command;

public class CommandSyntaxException extends RuntimeException {
    private final Command command;

    public CommandSyntaxException(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
