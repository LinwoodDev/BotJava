package com.github.linwoodcloud.bot.core.config;

/**
 * @author CodeDoctorDE
 */
public class DatabaseConfig {
    private DatabaseType type;
    private String username;
    private String password;
    private String host;
    private String port;
    private String database;

    public DatabaseConfig(){

    }

    public DatabaseType getType() {
        return type;
    }

    public String getDatabase() {
        return database;
    }

    public String getHost() {
        return host;
    }

    public String getPassword() {
        return password;
    }

    public String getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setType(DatabaseType type) {
        this.type = type;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
