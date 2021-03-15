package com.github.linwoodcloud.bot.core.utils;

import com.github.linwoodcloud.bot.core.Linwood;
import com.github.linwoodcloud.bot.core.config.DatabaseConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author CodeDoctorDE
 */
public class DatabaseUtil {
    private static final Logger logger = LogManager.getLogger(DatabaseUtil.class);
    private Connection connection;
    private static DatabaseUtil instance;

    public static DatabaseUtil getInstance() {
        return instance;
    }

    public DatabaseUtil() {
        if(instance == null)
            instance = this;
    }

    public static DatabaseConfig getConfig() {
        return Linwood.getInstance().getConfig().getDatabase();
    }

    public void connect() {
        var config = Linwood.getInstance().getConfig().getDatabase();
        try {
            connection = DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected(){
        return connection != null;
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        if(!isConnected())
            return;
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection = null;
    }
    public void update(String query) {
        update(query, new Object[0]);
    }

    public void update(String query, Object[] params) {
        try{
            var statement = connection.prepareStatement(query);
            for (int i = 0; i < params.length; i++)
                statement.setObject(i+1, params[i]);
            statement.executeUpdate();
        }catch(SQLException e){
            connect();
            e.printStackTrace();
        }
    }
    public ResultSet query(String query) {
        return query(query, new Object[0]);
    }

    public ResultSet query(String query, Object[] params) {
        try{
            var statement = connection.prepareStatement(query);
            for (int i = 0; i < params.length; i++)
                statement.setObject(i+1, params[i]);
            return statement.executeQuery();
        }catch(SQLException e){
            connect();
            e.printStackTrace();
        }
        return null;
    }
}
