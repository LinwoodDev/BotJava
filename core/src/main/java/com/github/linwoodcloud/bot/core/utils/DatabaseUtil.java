package com.github.linwoodcloud.bot.core.utils;

import com.github.linwoodcloud.bot.core.Linwood;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author CodeDoctorDE
 */
public class DatabaseUtil {
    private static final Logger logger = LogManager.getLogger(DatabaseUtil.class);
    private Connection connection;

    public DatabaseUtil() {

    }
    public void connect() throws SQLException {
        var config = Linwood.getInstance().getConfig().getDatabase();
        connection = DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword());
    }

    public boolean isConnected(){
        return connection != null;
    }

    public Connection getConnection() {
        return connection;
    }

    public void disconnect() throws SQLException {
        if(connection == null)
            return;
        connection.close();
        connection = null;
    }
}
