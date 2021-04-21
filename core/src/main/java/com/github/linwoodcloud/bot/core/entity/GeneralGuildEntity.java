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
    private final String guildId;
    private String locale = Locale.ENGLISH.toLanguageTag();
    private final Set<String> enabledModules = new HashSet<>(Arrays.asList(Linwood.getInstance().getModulesStrings()));
    private String maintainerId = null;
    private String logChannel;
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

    public String getMaintainerId() {
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

    public void setMaintainerId(String maintainer) {
        this.maintainerId = maintainer;
    }

    public void setMaintainer(Role role){
        if(role == null)
            maintainerId = null;
        else
            maintainerId = role.getId();
    }

    public GuildPlan getPlan() {
        return plan;
    }

    public void setPlan(GuildPlan plan) {
        this.plan = plan;
    }
    public boolean addPrefix(String prefix){
        //if(plan.getPrefixLimit() < 0 || plan.getPrefixLimit() > getPrefixes().size())
        //    return getPrefixes().add(prefix);
        return false;
    }

    public static void create(){
        var config = DatabaseUtil.getConfig();
        update("CREATE TABLE IF NOT EXISTS `"+ config.getPrefix() + "guild` ( " +
                "`guild` VARCHAR(255) NOT NULL PRIMARY KEY , " +
                "`locale` VARCHAR(20) NULL DEFAULT NULL , " +
                "`maintainer` VARCHAR(255) NOT NULL , " +
                "`log_channel` VARCHAR(255) NOT NULL , " +
                "`plan` VARCHAR(50) NOT NULL" +
                ")");
        update("CREATE TABLE IF NOT EXISTS `"+ config.getPrefix() + "guild_prefixes` ( " +
                "`guild` VARCHAR(255) NOT NULL PREFERENCES "+ config.getPrefix() + "guild(guild) , " +
                "`prefix` VARCHAR NOT NULL PRIMARY KEY" +
                ")");
        LOGGER.info("Tables initialized!");
    }
    public static GeneralGuildEntity get(String guildId) {
        var rs = query("SELECT * FROM `" + getPrefix() + "guild WHERE guildId=`" + guildId);
        try {
            rs.next();
            GeneralGuildEntity entity = new GeneralGuildEntity(guildId);
            entity.locale = rs.getString("locale");
            entity.plan = GuildPlan.valueOf(rs.getString("plan"));
            entity.logChannel = rs.getString("log_channel");
            entity.maintainerId = rs.getString("maintainer");

            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public void insert() {
        update("INSERT INTO " + getPrefix() + "guild (guild, locale, maintainer, log_channel, plan) VALUES (?,?,?,?)", guildId, locale, maintainerId, logChannel, plan.name());
    }

    @Override
    public void save() {
        update("UPDATE " + getPrefix() + "guild SET locale=?, maintainer=?, log_channel=?, plan=? WHERE guild=?", locale, maintainerId, logChannel, plan.name(), guildId);
    }
}
