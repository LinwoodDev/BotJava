package com.github.codedoctorde.linwood.utils;

import com.github.codedoctorde.linwood.entity.ServerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class DatabaseUtil {
    private static final Logger logger = LogManager.getLogger(DatabaseUtil.class);
    private final SessionFactory sessionFactory;

    public DatabaseUtil(){
        try {
            createFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        var configuration = new Configuration();
        configuration.configure();

        var prop = (PropertyResourceBundle) ResourceBundle.getBundle("db");

// Basic connection information
        configuration.setProperty("hibernate.connection.username", prop.getString("db.username"));
        configuration.setProperty("hibernate.connection.password", prop.getString("db.password"));
        configuration.setProperty("hibernate.connection.url", prop.getString("db.url"));
        configuration.setProperty("hibernate.connection.driver_class", prop.getString("db.driver"));
        configuration.setProperty("hibernate.dialect", prop.getString("db.dialect"));
        configuration.setProperty("hibernate.default_schema", prop.getString("db.default_schema"));

        configuration.setProperty("hibernate.current_session_context_class", prop.getString("hibernate.session_context"));

// Handling SQL statements in the logs
        configuration.setProperty("show_sql", prop.getString("hibernate.show_sql"));
        configuration.setProperty("format_sql", prop.getString("hibernate.format_sql"));
        configuration.setProperty("use_sql_comments", prop.getString("hibernate.use_sql_comments"));

// C3P0 Settings
        configuration.setProperty("hibernate.c3p0.min_size", prop.getString("hibernate.c3p0.min_size"));
        configuration.setProperty("hibernate.c3p0.max_size", prop.getString("hibernate.c3p0.max_size"));
        configuration.setProperty("hibernate.c3p0.timeout", prop.getString("hibernate.c3p0.timeout"));
        configuration.setProperty("hibernate.c3p0.max_statements", prop.getString("hibernate.c3p0.max_statements"));
        configuration.setProperty("hibernate.c3p0.idle_test_period", prop.getString("hibernate.c3p0.idle_test_period"));
        configuration.setProperty("hibernate.c3p0.preferredTestQuery", prop.getString("hibernate.c3p0.preferredTestQuery"));
        sessionFactory = configuration.buildSessionFactory();
    }

    public void createFile() throws IOException {
        var file = new File("./db.properties");
        if(!file.exists()){
            Files.copy(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("db.properties")), file.toPath());
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }

    public ServerEntity getServerById(Session session, long serverId){
        return session.load(ServerEntity.class, serverId);
    }
    public ServerEntity createServer(Session session, long serverId){
        var server = new ServerEntity(serverId);
        session.beginTransaction();
        session.save(server);
        session.getTransaction().commit();
        return server;
    }
}
