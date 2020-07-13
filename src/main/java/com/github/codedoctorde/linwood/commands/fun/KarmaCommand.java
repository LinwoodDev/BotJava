package com.github.codedoctorde.linwood.commands.fun;

import com.github.codedoctorde.linwood.Linwood;
import com.github.codedoctorde.linwood.commands.Command;
import com.github.codedoctorde.linwood.commands.settings.karma.LikeCommand;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.RestAction;
import org.hibernate.Session;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class KarmaCommand implements Command {
    @Override
    public boolean onCommand(Session session, Message message, GuildEntity entity, String label, String[] args) {
        if(args.length > 1)
        return false;
        var bundle = getBundle(entity);
        var pattern = Pattern.compile("<@(?!|)(\\d+)>|(\\d+)");
        if(args.length == 0)
            karmaCommand(entity, Objects.requireNonNull(message.getMember()), message.getTextChannel());
        else{
            var matcher = pattern.matcher(args[0]);
            assert bundle != null;

            if (matcher.find()) {
                String memberId;
                System.out.println(matcher.groupCount());
                if (matcher.groupCount() == 2){
                    var mentionGroup = matcher.group(0);
                    var idGroup = matcher.group(1);
                    if(mentionGroup != null)
                        memberId = mentionGroup;
                    else
                        memberId = idGroup;
                }
                else {
                    message.getChannel().sendMessage(bundle.getString("Invalid")).queue();
                    return true;
                }
                try {
                    message.getGuild().retrieveMemberById(memberId).queue(member -> karmaCommand(entity, member, message.getTextChannel()));
                }catch(Exception ignored){
                    message.getChannel().sendMessage(bundle.getString("Invalid")).queue();
                }
            }
        }
        return true;
    }
    public void karmaCommand(GuildEntity entity, Member member, TextChannel channel){
        var bundle = getBundle(entity);
        assert bundle != null;
        var session = Linwood.getInstance().getDatabase().getSessionFactory().openSession();
        var memberEntity = Linwood.getInstance().getDatabase().getMemberEntity(session, member);
        channel.sendMessage(" ").embed(new EmbedBuilder()
                .setTitle(bundle.getString("Title"))
                .setDescription(bundle.getString("Body"))
                .setColor(new Color(0x3B863B))
                .setTimestamp(LocalDateTime.now())
                .setAuthor(member.getUser().getAsTag(), "https://discordapp.com", member.getUser().getAvatarUrl())
                .addField(bundle.getString("KarmaPoints"), String.valueOf(memberEntity.getKarma()), true)
                .addField(bundle.getString("Likes"), String.valueOf(memberEntity.getLikes()), true)
                .addField(bundle.getString("Dislikes"), String.valueOf(memberEntity.getDislikes()), true)
                .addField(bundle.getString("Level"), String.valueOf(memberEntity.getLevel(session)), false)
                .addField(bundle.getString("Experience"), Math.round(memberEntity.getRemainingKarma(session) * 100 * 100) / 100  + "%", true)
                .addField(bundle.getString("Rank"), "invalid", true)
                .build()).queue();
        session.close();
    }

    @Override
    public String[] aliases(GuildEntity entity) {
        return new String[]{
                "karma",
                "likes",
                "level",
                "levels",
                "rank"
        };
    }

    @Override
    public @Nullable ResourceBundle getBundle(GuildEntity entity) {
        return ResourceBundle.getBundle("locale.commands.fun.Karma", entity.getLocalization());
    }
}
