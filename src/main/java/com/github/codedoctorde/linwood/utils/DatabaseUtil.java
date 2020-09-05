package com.github.codedoctorde.linwood.utils;

import com.github.codedoctorde.linwood.Linwood;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import com.github.codedoctorde.linwood.entity.MemberEntity;
import com.sun.istack.Nullable;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.sentry.Sentry;
import net.dv8tion.jda.api.entities.Member;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * @author CodeDoctorDE
 */
public class DatabaseUtil {
    private static final Logger logger = LogManager.getLogger(DatabaseUtil.class);
    private final HikariConfig config;
    private final HikariDataSource dataSource;

    public DatabaseUtil() {
        try {
            createFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        var prop = new Properties();
        try {
            prop.load(new FileReader("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        config = new HikariConfig(prop);
        dataSource = new HikariDataSource(config);
    }

    public void createFile() throws IOException {
        var file = new File("./db.properties");
        if(!file.exists()){
            Files.copy(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("db.properties")), file.toPath());
        }
    }

    public HikariConfig getConfig() {
        return config;
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    public Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException throwables) {
            Sentry.capture(throwables);
            throwables.printStackTrace();
        }
        return null;
    }

    public GuildEntity getGuildById(Session session, long guildId){
        var connection = getConnection();
        connection.prepareStatement("SELECT ");
        GuildEntity entity = session.get(GuildEntity.class, guildId);
        if ( entity != null ) return entity;
        else try {
            return createGuild(session, guildId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public GuildEntity createGuild(Session session, long guildId){
        var guild = new GuildEntity(guildId);
        guild.save(session);
        return guild;
    }
    public void updateEntity(Session session, GuildEntity entity){
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
        var cq = builder.createQuery(GuildEntity.class);
        var from = cq.from(GuildEntity.class);
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
    public MemberEntity[] getKarmaLeaderboard(Session session, @Nullable Long guild){
        return getKarmaLeaderboard(session, guild, 20);
    }

    public MemberEntity[] getKarmaLeaderboard(Session session, @Nullable Long guild, int maxResults){
// Create CriteriaBuilder
        var builder = session.getCriteriaBuilder();

// Create CriteriaQuery
        var cq = builder.createQuery(MemberEntity.class);
        var member = cq.from(MemberEntity.class);
        var all = cq.select(member);
        if(guild != null)
            all.where(builder.equal(member.get("guildId"), guild));
        all.orderBy(builder.desc(member.get("likes")), builder.asc(member.get("dislikes")));
        var allQuery = session.createQuery(all);
        allQuery.setMaxResults(maxResults);
        return allQuery.getResultList().toArray(new MemberEntity[0]);
    }

    public MemberEntity getMemberEntity(Session session, Member member){
        return getMemberById(session, member.getGuild().getIdLong(), member.getIdLong());
    }

    public MemberEntity getMemberById(Session session, long guildId, long memberId){
        var cb = session.getCriteriaBuilder();
        var cq = cb.createQuery(MemberEntity.class);
        var from = cq.from(MemberEntity.class);

        cq = cq.select(from).where(
                cb.equal(from.get("guildId"), guildId),
                cb.equal(from.get("memberId"), memberId)
        );
        var result = session.createQuery(cq).list();
        if(result.size() < 1)
            return createMember(session, guildId, memberId);
        return result.get(0);
    }

    private MemberEntity createMember(Session session, long guildId, long memberId) {
        var member = new MemberEntity(guildId, memberId);
        member.save(session);
        return member;
    }
}
