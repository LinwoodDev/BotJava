package com.github.linwoodcloud.bot.core.entity;

import com.github.linwoodcloud.bot.core.Linwood;
import com.github.linwoodcloud.bot.core.utils.DatabaseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 * @author CodeDoctorDE
 */
public class GeneralMemberEntity extends MemberEntity {
    private final long id;
    private final String memberId;
    private final String guildId;
    private String locale = null;
    private static final Logger LOGGER = LogManager.getLogger(GeneralMemberEntity.class);

    public GeneralMemberEntity(long id, String guildId, String memberId) {
        this.id = id;
        this.guildId = guildId;
        this.memberId = memberId;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getMemberId() {
        return memberId;
    }

    public static void create(){
        var config = DatabaseUtil.getConfig();
        update("CREATE TABLE IF NOT EXISTS `"+ config.getPrefix() + "member` ( " +
                "`id` BIGINT NOT NULL PRIMARY KEY , " +
                "`member` VARCHAR(255) NOT NULL , " +
                "`guild` VARCHAR(255) NOT NULL , " +
                "`locale` VARCHAR(20) NULL DEFAULT NULL" +
                ")");
        LOGGER.info("Tables initialized!");
    }
    public static GeneralMemberEntity get(String guildId, String memberId) {
        var rs = query("SELECT * FROM `" + getPrefix() + "guild WHERE guild=? AND member=?");
        try {
            rs.next();
            GeneralMemberEntity entity = new GeneralMemberEntity(rs.getLong("id"), guildId, memberId);
            entity.locale = rs.getString("locale");

            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public long getId() {
        return id;
    }

    @Override
    public void insert() {
        update("INSERT INTO " + getPrefix() + "member (locale, maintainer, log_channel, plan) VALUES (?,?,?,?)", locale, maintainerId, logChannel, plan.name());
    }

    @Override
    public void delete() {
        update("DELETE FROM " + getPrefix() + "guild WHERE guild=?", guildId);
    }

    @Override
    public String getGuildId() {
        return guildId;
    }
}
