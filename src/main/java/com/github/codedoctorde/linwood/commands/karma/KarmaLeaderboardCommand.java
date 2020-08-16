package com.github.codedoctorde.linwood.commands.karma;

import com.github.codedoctorde.linwood.Linwood;
import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import com.github.codedoctorde.linwood.entity.MemberEntity;
import com.github.codedoctorde.linwood.utils.DatabaseUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author CodeDoctorDE
 */
public class KarmaLeaderboardCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        if(args.length != 0)
        return false;
        var bundle = getBundle(entity);
        var leaderboard = Linwood.getInstance().getDatabase().getKarmaLeaderboard(session, message.getGuild().getIdLong());
        message.getGuild().retrieveMembersByIds(Arrays.stream(leaderboard).map(MemberEntity::getMemberId).collect(Collectors.toList())).onSuccess(members -> {
            var description = new StringBuilder();
            description.append(bundle.getString("LeaderboardBodyStart")).append("\n");
            for (int i = 0; i < members.size(); i++) {
                var member = members.get(i);
                var me = leaderboard[i];
                description.append(String.format(bundle.getString("LeaderboardBody"), i + 1, member.getUser().getAsMention(), me.getKarma(), me.getLikes(), me.getDislikes()));
            }
            description.append(bundle.getString("LeaderboardBodyEnd"));
            message.getChannel().sendMessage(new EmbedBuilder()
                    .setTitle(bundle.getString("LeaderboardHeader"))
                    .setFooter(bundle.getString("LeaderboardFooter"))
                    .setDescription(description)
                    .setColor(new Color(0x3B863B))
                    .setTimestamp(LocalDateTime.now())
                    .build()).queue();
        });
        return true;
    }

    @Override
    public @NotNull Set<String> aliases(GuildEntity entity) {
        return new HashSet<>(Arrays.asList(
                "leaderboard", "lb", "rank", "ranks", "top", "toplist", "top-list", "list"
        ));
    }

    @Override
    public @NotNull ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.commands.karma.Leaderboard");
    }
}