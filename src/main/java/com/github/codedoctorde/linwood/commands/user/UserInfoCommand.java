package com.github.codedoctorde.linwood.commands.user;

import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author CodeDoctorDE
 */
public class UserInfoCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        if(args.length != 0)
        return false;
        if(message.getMember() == null)
            return true;
        var author = message.getAuthor();
        var member = message.getMember();
        var bundle = getBundle(entity);
        message.getChannel().sendMessage(new EmbedBuilder()
                .setThumbnail(author.getAvatarUrl())
                .addField(bundle.getString("Created"), String.format(bundle.getString("CreatedBody"), author.getTimeCreated()), true)
                .addField(bundle.getString("Joined"), member.hasTimeJoined()? String.format(bundle.getString("JoinedBody"), member.getTimeJoined(), Duration.between(member.getTimeJoined(), OffsetDateTime.now()).toDays()):bundle.getString("JoinedBodyInvalid"), true)
                .addField(bundle.getString("SharedGuilds"), String.format(bundle.getString("SharedGuildsBody"),author.getMutualGuilds().size()), true)
                .build()).queue();
        return true;
    }

    @Override
    public @NotNull Set<String> aliases(GuildEntity entity) {
        return new HashSet<>(Arrays.asList("i", "info", "information"));
    }

    @Override
    public @NotNull ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.commands.user.Info", entity.getLocalization());
    }
}
