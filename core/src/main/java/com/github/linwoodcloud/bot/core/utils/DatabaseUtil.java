package com.github.linwoodcloud.bot.core.utils;

import com.github.linwoodcloud.bot.core.Linwood;
import com.github.linwoodcloud.bot.core.entity.*;
import com.github.linwoodcloud.bot.core.entity.GeneralGuildEntity;
import com.github.linwoodcloud.bot.core.entity.GeneralMemberEntity;
import com.github.linwoodcloud.bot.core.entity.GuildEntity;
import com.github.linwoodcloud.bot.core.entity.MemberEntity;
import net.dv8tion.jda.api.entities.Member;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class DatabaseUtil {
    private static final Logger logger = LogManager.getLogger(DatabaseUtil.class);
    private SessionFactory sessionFactory;

    public DatabaseUtil() {
        try {
            createFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void rebuildSessionFactory(){

        var configuration = new Configuration();
        configuration.configure();

        ResourceBundle prop;
        try (FileInputStream fis = new FileInputStream("db.properties")) {
            prop =  new PropertyResourceBundle(fis);
        } catch (IOException e) {
            e.printStackTrace();
            prop = ResourceBundle.getBundle("db");
        }

// Basic connection information
        configuration.setProperty("hibernate.connection.username", prop.getString("db.username"));
        configuration.setProperty("hibernate.connection.password", prop.getString("db.password"));
        var url = prop.getString("db.url");
        if(url.isBlank())
            configuration.setProperty("hibernate.connection.url", System.getenv("JDBC_DATABASE_URL"));
        else
            configuration.setProperty("hibernate.connection.url", url);
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
        Arrays.stream(Linwood.getInstance().getModules()).forEach(module -> module.getEntities().forEach(configuration::addClass));
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

    public <T extends GuildEntity> T getGuildEntityById(Class<T> aClass, Session session, long id){
        T entity = session.get(aClass, id);
        if ( entity != null ) return entity;
        else try {
            return createGuildEntity(aClass, session, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public GeneralGuildEntity getGuildById(Session session, long guildId){
        return getGuildEntityById(GeneralGuildEntity.class, session, guildId);
    }
    public <T extends GuildEntity> T createGuildEntity(Class<T> aClass, Session session, long id) {
        T guild = null;
        try {
            guild = aClass.getDeclaredConstructor(Long.class).newInstance(id);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
        assert guild != null;
        guild.save(session);
        return guild;
    }
    public GeneralGuildEntity createGeneralGuildEntity(Session session, long guildId){
        return createGuildEntity(GeneralGuildEntity.class, session, guildId);
    }
    public void updateEntity(Session session, GeneralGuildEntity entity){
        session.update(entity);
    }

    public void cleanup(Session session){
        cleanupGuilds(session);
    }

    private void cleanupGuilds(Session session) {
        logger.info("Guild clean up...");
// Create CriteriaBuilder
        var builder = session.getCriteriaBuilder();

// Create CriteriaQuery
        var cq = builder.createQuery(GeneralGuildEntity.class);
        var from = cq.from(GeneralGuildEntity.class);
        var all = cq.select(from);
        var allQuery = session.createQuery(all);
        int count = 0;
        for (var entity :
                allQuery.getResultList())
            if (Linwood.getInstance().getJda().getGuildById(entity.getGuildId()) == null) {
                session.delete(entity);
                count++;
            }
        logger.info("Successfully clean up " + count + " guilds!");
    }

    public <T extends MemberEntity> T getMember(Class<T> aClass, Session session, Member member){
        return getMemberById(aClass, session, member.getGuild().getIdLong(), member.getIdLong());
    }

    public GeneralMemberEntity getGeneralMember(Session session, Member member){
        return getMember(GeneralMemberEntity.class, session, member);
    }

    public <T extends MemberEntity> T getMemberById(Class<T> aClass, Session session, long guildId, long memberId){
        var cb = session.getCriteriaBuilder();
        var cq = cb.createQuery(aClass);
        var from = cq.from(aClass);

        cq = cq.select(from).where(
                cb.equal(from.get("guildId"), guildId),
                cb.equal(from.get("memberId"), memberId)
        );
        var result = session.createQuery(cq).list();
        if(result.size() < 1)
            return createMember(aClass, session, guildId, memberId);
        return result.get(0);
    }

    public GeneralMemberEntity getGeneralMemberById(Session session, long guildId, long memberId){
        return getMemberById(GeneralMemberEntity.class, session, guildId, memberId);
    }

    public <T extends MemberEntity> T createMember(Class<T> aClass, Session session, long guildId, long memberId){
        T member = null;
        try {
            member = aClass.getDeclaredConstructor(Long.class, Long.class).newInstance(guildId, memberId);
        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        assert member != null;
        member.save(session);
        return member;
    }

    private GeneralMemberEntity createGeneralMember(Session session, long guildId, long memberId) {
        return createMember(GeneralMemberEntity.class, session, guildId, memberId);
    }
}
