package com.github.codedoctorde.linwood.commands;

import com.github.codedoctorde.linwood.Main;
import com.github.codedoctorde.linwood.entity.ServerEntity;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class InfoCommand implements Command {

    @Override
    public boolean onCommand(Session session, Message message, ServerEntity entity, String label, String[] args) {
        var bundle = getBundle(entity);
        if(args.length > 0)
            return false;
        message.getChannel().sendMessage(" ").embed(new EmbedBuilder().setTitle(infoFormat(session, message, bundle.getString("title")))
                .setDescription(infoFormat(session, message, bundle.getString("body.regexp"))).build()).queue();
        return true;
    }

    public String infoFormat(Session session, Message message, String text){
        ServerEntity server = Main.getInstance().getDatabase().getServerById(session, message.getGuild().getIdLong());
        return MessageFormat.format(text, Main.getInstance().getVersion(), message.getAuthor().getAsMention(), server.getPrefix());
    }

    public ResourceBundle getBundle(ServerEntity entity){
        return ResourceBundle.getBundle("locale.commands.Info", entity.getLocalization());
    }

    @Override
    public String[] aliases() {
        return new String[]{
                "", "info", "i", "information"
        };
    }

    @Override
    public String description(ServerEntity entity) {
        var bundle = getBundle(entity);
        return bundle.getString("Description");
    }

    @Override
    public String syntax(ServerEntity entity) {
        var bundle = getBundle(entity);
        return bundle.getString("Syntax");
    }
}
