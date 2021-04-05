package com.github.linwoodcloud.bot.core.entity;

import com.github.linwoodcloud.bot.core.Linwood;
import com.github.linwoodcloud.bot.core.module.LinwoodModule;
import com.github.linwoodcloud.bot.core.utils.GuildLogLevel;
import com.github.linwoodcloud.bot.core.utils.DatabaseUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.*;

/**
 * @author CodeDoctorDE
 */
public class GeneralGuildEntity extends GuildEntity {
    private String guildId;
    private final Set<String> prefixes = new HashSet<>(Linwood.getInstance().getConfig().getPrefixes());
    private String locale = Locale.ENGLISH.toLanguageTag();
    private final Set<String> enabledModules = new HashSet<>(Arrays.asList(Linwood.getInstance().getModulesStrings()));
    private Long maintainerId = null;
    private Long logChannel;
    private static final Logger LOGGER = LogManager.getLogger(GeneralGuildEntity.class);
    private GuildPlan plan = GuildPlan.COMMUNITY;

    public GeneralGuildEntity(String id) {
        this.guildId = id;
    }

    public String getGuildId() {
        return guildId;
    }

    public String getLocale() {
        return locale;
    }

    public Locale getLocalization(){
        return Locale.forLanguageTag(locale);
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }



    public Long getMaintainerId() {
        return maintainerId;
    }
    public Role getMaintainer(){
        if(maintainerId == null)
            return null;
        return Linwood.getInstance().getJda().getRoleById(maintainerId);
    }
    public String translate(String namespace, String key){
        var bundle = ResourceBundle.getBundle("locale." + namespace, getLocalization());
        return bundle.containsKey(key) ? bundle.getString(key) : key;
    }
    public void log(GuildLogLevel level, LinwoodModule module, String message){
        try {
            if (logChannel != null) {
                if (Arrays.asList(Linwood.getInstance().getModules()).contains(module))
                    Objects.requireNonNull(Objects.requireNonNull(Linwood.getInstance().getJda().getGuildById(guildId)).getTextChannelById(logChannel))
                            .sendMessage(new EmbedBuilder().setTitle(module.getName()).setColor(level.getColor()).setDescription(message).build()).queue();
            }
        }catch(Exception ignored){}
    }
    public void enableModule(LinwoodModule module){
        enabledModules.add(module.getName());
    }
    public void disableModule(LinwoodModule module){
        enabledModules.remove(module.getName());
    }

    public Set<String> getEnabledModules() {
        return Set.copyOf(enabledModules);
    }

    public void setMaintainerId(Long maintainer) {
        this.maintainerId = maintainer;
    }

    public void setMaintainer(Role role){
        if(role == null)
            maintainerId = null;
        else
            maintainerId = role.getIdLong();
    }

    public Set<String> getPrefixes() {
        return prefixes;
    }

    public GuildPlan getPlan() {
        return plan;
    }

    public void setPlan(GuildPlan plan) {
        this.plan = plan;
    }
    public boolean addPrefix(String prefix){
        if(plan.getPrefixLimit() < 0 || plan.getPrefixLimit() > getPrefixes().size())
            return getPrefixes().add(prefix);
        return false;
    }

    public static void create(){
        var config = DatabaseUtil.getConfig();
        DatabaseUtil.getInstance().update("CREATE TABLE IF NOT EXISTS `"+ config.getPrefix() + "guild` ( `guild` BIGINT NOT NULL PRIMARY KEY , `locale` VARCHAR NULL DEFAULT NULL , `maintainer` BIGINT NOT NULL , `log_channel` BIGINT NOT NULL , `plan` VARCHAR NOT NULL );");
        DatabaseUtil.getInstance().update("CREATE TABLE IF NOT EXISTS `"+ config.getPrefix() + "guild_prefixes` ( `guild` BIGINT NOT NULL PREFERENCES "+ config.getPrefix() + "guild(guild) , `prefix` VARCHAR NOT NULL PRIMARY KEY) ;");
        LOGGER.info("Tables initialized!");
    }
    public static GeneralGuildEntity get(String guildId) {
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

    @Override
    public void save() {

    }

    @Override
    public void delete() {

    }
}
