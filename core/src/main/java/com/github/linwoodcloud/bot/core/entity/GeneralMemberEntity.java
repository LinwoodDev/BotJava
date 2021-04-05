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
    private Long id;
    private long memberId;
    private long guildId;
    private String locale = null;
    private static final Logger LOGGER = LogManager.getLogger(GeneralMemberEntity.class);

    public GeneralMemberEntity(long guildId, long memberId) {
        this.guildId = guildId;
        this.memberId = memberId;
    }
    public GeneralMemberEntity(){}

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public long getMemberId() {
        return memberId;
    }

    public static void create(){
        var config = DatabaseUtil.getConfig();
        DatabaseUtil.getInstance().update("CREATE TABLE IF NOT EXISTS `"+ config.getPrefix() + "guild` ( `guild` BIGINT NOT NULL PRIMARY KEY , `locale` VARCHAR NULL DEFAULT NULL , `maintainer` BIGINT NOT NULL , `log_channel` BIGINT NOT NULL , `plan` VARCHAR NOT NULL );");
        DatabaseUtil.getInstance().update("CREATE TABLE IF NOT EXISTS `"+ config.getPrefix() + "guild_prefixes` ( `guild` BIGINT NOT NULL PREFERENCES "+ config.getPrefix() + "guild(guild) , `prefix` VARCHAR NOT NULL PRIMARY KEY) ;");
        LOGGER.info("Tables initialized!");
    }
    public static GeneralMemberEntity get(String guildId) {
        var rs = DatabaseUtil.getInstance().query("SELECT * FROM `" + DatabaseUtil.getConfig().getPrefix() + "guild WHERE guildId=`" + guildId);
        try {
            rs.next();
            GeneralGuildEntity entity = new GeneralGuildEntity(guildId);
            entity.locale = rs.getString("locale");
            entity.plan = GuildPlan.valueOf(rs.getString("plan"));
            entity.logChannel = rs.getLong("log_channel");
            entity.maintainerId = rs.getLong("maintainer");

            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public String getGuildId() {
        return guildId;
    }

    public Long getId() {
        return id;
    }
    public GeneralGuildEntity getGuild(Session session) {
        return Linwood.getInstance().getDatabase().getGuildById(session, guildId);
    }

    @Override
    public void save() {

    }

    @Override
    public void delete() {

    }
}
