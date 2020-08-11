package com.github.codedoctorde.linwood.entity;

import com.github.codedoctorde.linwood.Linwood;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

import javax.persistence.*;

@Entity
@Table(name = "notification")
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column
    private Long teamRoleId = null;
    @Column
    private Long supportChatId = null;
    @Column
    private Long statusChatId = null;
    @Column
    private Long logChatId = null;
    @OneToOne(mappedBy = "notificationEntity")
    private GuildEntity guild;

    public Long getId() {
        return id;
    }

    public GuildEntity getGuild() {
        return guild;
    }


    public Role getTeamRole(){
        if(teamRoleId == null)
            return null;
        return Linwood.getInstance().getJda().getRoleById(teamRoleId);
    }

    public Long getTeamRoleId() {
        return teamRoleId;
    }

    public void setTeamRole(Role teamRole){
        if(teamRole == null)
            teamRoleId = null;
        else
            teamRoleId = teamRole.getIdLong();
    }

    public void setTeamRoleId(Long teamRoleId) {
        this.teamRoleId = teamRoleId;
    }

    @Nullable
    public Long getSupportChatId() {
        return supportChatId;
    }
    public TextChannel getSupportChat() {
        if(supportChatId == null)
            return null;
        else
            return Linwood.getInstance().getJda().getTextChannelById(supportChatId);
    }
    public void setSupportChat(TextChannel chat){
        if(chat == null)
            supportChatId = null;
        else
            supportChatId = chat.getIdLong();
    }

    public void setSupportChatId(Long supportChatId) {
        this.supportChatId = supportChatId;
    }

    public Long getStatusChatId() {
        return statusChatId;
    }
    public TextChannel getStatusChat()  {
        if(statusChatId == null)
            return null;
        else
            return Linwood.getInstance().getJda().getTextChannelById(statusChatId);
    }

    public void setStatusChatId(Long statusChatId) {
        this.statusChatId = statusChatId;
    }
    public void setStatusChat(TextChannel chat){
        if(chat == null)
            statusChatId = null;
        else
            statusChatId = chat.getIdLong();
    }

    public Long getLogChatId() {
        return logChatId;
    }
    public TextChannel getLogChat() {
        if(logChatId == null)
            return null;
        else
            return Linwood.getInstance().getJda().getTextChannelById(logChatId);
    }

    public void setLogChatId(Long logChatId) {
        this.logChatId = logChatId;
    }
    public void setLogChat(TextChannel chat){
        if(chat == null)
            logChatId = null;
        else
            logChatId = chat.getIdLong();
    }
}
