package com.github.codedoctorde.linwood.core.commands;

import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.hibernate.Session;

import java.util.Arrays;

/**
 * @author CodeDoctorDE
 */
public class CommandEvent {
    private final Message message;
    private final Session session;
    private final GuildEntity entity;
    private final String label;
    private final String[] args;

    public CommandEvent(Message message, Session session, GuildEntity entity, String label, String[] args){
        this.message = message;
        this.session = session;
        this.entity = entity;
        this.label = label;
        this.args = args;
    }

    public GuildEntity getEntity() {
        return entity;
    }

    public Message getMessage() {
        return message;
    }

    public Session getSession() {
        return session;
    }

    public String getLabel() {
        return label;
    }

    public String[] getArgs() {
        return args;
    }
    public TextChannel getTextChannel(){
        return message.getTextChannel();
    }
    public MessageAction reply(Message message){
        return getTextChannel().sendMessage(message);
    }
    public MessageAction reply(MessageEmbed embed){
        return getTextChannel().sendMessage(embed);
    }
    public MessageAction reply(String text){
        return getTextChannel().sendMessage(text);
    }
    public MessageAction replyFormat(String format, Object... args){
        return getTextChannel().sendMessageFormat(format, args);
    }
    public Member getMember(){
        return getMessage().getMember();
    }
    public CommandEvent upper(){
        return new CommandEvent(message, session, entity, (args.length > 0) ? args[0] : "",
                (args.length > 0) ? Arrays.copyOfRange(args, 1, args.length) : new String[0]);
    }
}
