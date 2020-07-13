package com.github.codedoctorde.linwood.listener;

import com.github.codedoctorde.linwood.Linwood;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import com.ibm.icu.util.LocaleData;
import com.mysql.cj.protocol.x.XMessage;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class KarmaListener {
    private final HashMap<Long, Integer> memberGivingHashMap = new HashMap<>();
    private LocalDate lastReset = LocalDate.now();
    private final Set<Long> disabledChannels = new HashSet<>();

    @SubscribeEvent
    public void onGive(MessageReactionAddEvent event){
        if(disabledChannels.contains(event.getChannel().getIdLong()))
            return;
        event.retrieveMember().queue(donor -> {
            var emote = event.getReactionEmote().getAsReactionCode();
            var session = Linwood.getInstance().getDatabase().getSessionFactory().openSession();
            var entity = Linwood.getInstance().getDatabase().getGuildById(session, event.getGuild().getIdLong());
            var karma = entity.getKarmaEntity();
            session.close();
            event.getChannel().retrieveMessageById(event.getMessageIdLong()).queue(message -> event.getGuild().retrieveMember(message.getAuthor()).queue(taker -> {
                var session1 = Linwood.getInstance().getDatabase().getSessionFactory().openSession();
                if(karma.getLikeEmote() == null)
                    return;
                boolean works = true;
                if (taker == null || taker.getUser().isBot() || donor.getUser().isBot()) return;
                if(donor.equals(taker)) {
                    works = false;
                    giveLike(entity, donor, taker, session1);
                    memberGivingHashMap.put(donor.getIdLong(), memberGivingHashMap.getOrDefault(donor.getIdLong(), 0) - 1);
                }
                else if(emote.equals(karma.getLikeEmote()))
                    works = giveLike(entity, donor, taker, session1);
                else if(emote.equals(karma.getDislikeEmote()))
                    works = giveDislike(entity, donor, taker, session1);
                if(!works)
                    event.getReaction().removeReaction(donor.getUser()).queue();
                session1.close();
            }));
        });
    }
    @SubscribeEvent
    public void onRemove(MessageReactionRemoveEvent event){
        if(disabledChannels.contains(event.getChannel().getIdLong()))
            return;
        event.retrieveMember().queue(donor -> {
            var emote = event.getReactionEmote().getAsReactionCode();
            var session = Linwood.getInstance().getDatabase().getSessionFactory().openSession();
            var entity = Linwood.getInstance().getDatabase().getGuildById(session, event.getGuild().getIdLong());
            var karma = entity.getKarmaEntity();
            session.close();
            event.getChannel().retrieveMessageById(event.getMessageIdLong()).queue(message -> event.getGuild().retrieveMember(message.getAuthor()).queue(taker -> {
                var session1 = Linwood.getInstance().getDatabase().getSessionFactory().openSession();
                if (taker == null || taker.getUser().isBot() || donor.getUser().isBot() || donor.equals(taker)) return;
                if (karma.getLikeEmote() == null)
                    return;
                else if (emote.equals(karma.getLikeEmote()))
                    removeLike(donor, taker, session1);
                else if (emote.equals(karma.getDislikeEmote()))
                    removeDislike(donor, taker, session1);
                session1.close();
            }));
        });
    }
    public boolean givingAction(GuildEntity entity, Member member){
        if(entity.getKarmaEntity().getMaxGiving() <= memberGivingHashMap.getOrDefault(member.getIdLong(), 0))
            return false;
        memberGivingHashMap.put(member.getIdLong(), memberGivingHashMap.getOrDefault(member.getIdLong(), 0) + 1);
        return true;
    }

    public boolean giveLike(GuildEntity entity, Member donor, Member taker, Session session){
        if(!givingAction(entity, donor))
            return false;
        var donorEntity = Linwood.getInstance().getDatabase().getMemberEntity(session, donor);
        var takerEntity = Linwood.getInstance().getDatabase().getMemberEntity(session, taker);
        takerEntity.setLikes(takerEntity.getLikes() + donorEntity.getLevel(session) + 1);
        takerEntity.save(session);
        return true;
    }
    public void removeLike(Member donor, Member taker, Session session){
        System.out.println("4");
        var donorEntity = Linwood.getInstance().getDatabase().getMemberEntity(session, donor);
        var takerEntity = Linwood.getInstance().getDatabase().getMemberEntity(session, taker);
        takerEntity.setLikes(takerEntity.getLikes() - donorEntity.getLevel(session) - 1);
        memberGivingHashMap.put(donor.getIdLong(), memberGivingHashMap.getOrDefault(donor.getIdLong(), 0) - 1);
        takerEntity.save(session);
    }
    public boolean giveDislike(GuildEntity entity, Member donor, Member taker, Session session){
        if(!givingAction(entity, donor))
            return false;
        var donorEntity = Linwood.getInstance().getDatabase().getMemberEntity(session, donor);
        var takerEntity = Linwood.getInstance().getDatabase().getMemberEntity(session, taker);
        takerEntity.setDislikes(takerEntity.getDislikes() + donorEntity.getLevel(session) + 1);
        takerEntity.save(session);
        return true;
    }
    public void removeDislike(Member donor, Member taker, Session session){
        var donorEntity = Linwood.getInstance().getDatabase().getMemberEntity(session, donor);
        var takerEntity = Linwood.getInstance().getDatabase().getMemberEntity(session, taker);
        takerEntity.setDislikes(takerEntity.getDislikes() - donorEntity.getLevel(session) - 1);
        memberGivingHashMap.put(donor.getIdLong(), memberGivingHashMap.getOrDefault(donor.getIdLong(), 0) - 1);
        takerEntity.save(session);
    }


    public boolean maybeReset(){
        var now = LocalDate.now();
        if(!now.isAfter(lastReset))
            return false;
        memberGivingHashMap.clear();
        lastReset = now;
        return true;
    }

    public HashMap<Long, Integer> getMemberGivingHashMap() {
        return memberGivingHashMap;
    }

    public LocalDate getLastReset() {
        return lastReset;
    }

    public Set<Long> getDisabledChannels() {
        return disabledChannels;
    }
}
