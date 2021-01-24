package com.github.codedoctorde.linwood.karma.commands;

import com.github.codedoctorde.linwood.core.Linwood;
import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.core.entity.GeneralMemberEntity;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author CodeDoctorDE
 */
public class KarmaLeaderboardCommand extends Command {
    @Override
    public boolean onCommand(final CommandEvent event) {
        if(event.getArguments().length != 0)
        return false;
        var leaderboard = Linwood.getInstance().getDatabase().getKarmaLeaderboard(event.getSession(), event.getMessage().getGuild().getIdLong());
        event.getMessage().getGuild().retrieveMembersByIds(Arrays.stream(leaderboard).map(GeneralMemberEntity::getMemberId).collect(Collectors.toList())).onSuccess(members -> {
            var description = new StringBuilder();
            description.append(getTranslationString(entity, "LeaderboardBodyStart")).append("\n");
            for (int i = 0; i < leaderboard.length; i++) {
                int finalI = i;
                var member = members.stream().filter(current -> current.getIdLong() == leaderboard[finalI].getMemberId()).findFirst().orElse(null);
                var me = leaderboard[i];
                if(member != null)
                description.append(String.format(getTranslationString(entity, "LeaderboardBody"), i + 1, member.getUser().getAsMention(), me.getKarma(), me.getLikes(), me.getDislikes()));
            }
            description.append(getTranslationString(entity, "LeaderboardBodyEnd"));
            event.reply(new EmbedBuilder()
                    .setTitle(getTranslationString(entity, "LeaderboardHeader"))
                    .setFooter(getTranslationString(entity, "LeaderboardFooter"))
                    .setDescription(description)
                    .setColor(new Color(0x3B863B))
                    .setTimestamp(LocalDateTime.now())
                    .build()).queue();
        });
        return true;
    }

    public KarmaLeaderboardCommand() {
        super(
                "leaderboard", "lb", "rank", "ranks", "top", "toplist", "top-list", "list"
        );
    }
}