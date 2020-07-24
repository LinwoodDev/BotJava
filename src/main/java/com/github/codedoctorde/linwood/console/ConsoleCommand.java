package com.github.codedoctorde.linwood.console;

/**
 * @author CodeDoctorDE
 */
public interface ConsoleCommand {
    boolean onCommand(final String label, final String[] args);
    String[] aliases();
    String description();
    String syntax();
}
