package com.github.codedoctorde.linwood.config;

import com.github.codedoctorde.linwood.commands.BaseCommand;

/**
 * @author CodeDoctorDE
 */
public class DatabaseConfig {
    private int databaseVersion = 1;

    public int getDatabaseVersion() {
        return databaseVersion;
    }

    public void setDatabaseVersion(int databaseVersion) {
        this.databaseVersion = databaseVersion;
    }
}
