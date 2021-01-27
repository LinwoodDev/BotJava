package com.github.codedoctorde.linwood.core.entity;

import com.github.codedoctorde.linwood.core.Linwood;
import com.github.codedoctorde.linwood.core.module.LinwoodModule;
import com.github.codedoctorde.linwood.core.utils.GuildLogLevel;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import org.hibernate.Session;

import javax.persistence.*;
import java.util.*;

/**
 * @author CodeDoctorDE
 */
@Entity
@Table(name = "guild")
public class GeneralGuildEntity extends GuildEntity {
    @Id
    @Column(name="id", unique = true, nullable = false)
    private long guildId;
    @ElementCollection
    @CollectionTable(name="Prefixes", joinColumns=@JoinColumn(name="guild_id"))
    @Column(name="prefix")
    private final Set<String> prefixes = new HashSet<>(Linwood.getInstance().getConfig().getPrefixes());
    private String locale = Locale.ENGLISH.toLanguageTag();
    @ElementCollection
    @CollectionTable(name="Prefixes", joinColumns=@JoinColumn(name="guild_id"))
    @Column(name="modules")
    private final Set<String> enabledModules = new HashSet<>(Arrays.asList(Linwood.getInstance().getModulesStrings()));
    @Column()
    private Long maintainerId = null;
    @Column()
    private Long logChannel;
    @Column()
    private GuildPlan plan = GuildPlan.COMMUNITY;

    public GeneralGuildEntity(){ }
    public GeneralGuildEntity(long id) {
        this.guildId = id;
    }

    public long getGuildId() {
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


    @Deprecated
    public static GeneralGuildEntity get(Session session, long guildId){
        return Linwood.getInstance().getDatabase().getGuildById(session, guildId);
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
}
