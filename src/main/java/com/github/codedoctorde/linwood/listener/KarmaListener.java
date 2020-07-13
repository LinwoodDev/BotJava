package com.github.codedoctorde.linwood.listener;

import com.github.codedoctorde.linwood.Linwood;
import com.github.codedoctorde.linwood.entity.GuildEntity;
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
    private HashMap<Long, Integer> memberGivingHashMap = new HashMap<>();
    private LocalDate lastReset = LocalDate.now();
    private Set<Long> disabledChannels = new HashSet<>();

    @SubscribeEvent
    public void onGive(MessageReactionAddEvent event){
        if(disabledChannels.contains(event.getChannel().getIdLong()) || event.getMember() == null)
            return;
        var emote = event.getReactionEmote().getAsCodepoints();
        var session = Linwood.getInstance().getDatabase().getSessionFactory().openSession();
        var entity = Linwood.getInstance().getDatabase().getGuildById(session, event.getGuild().getIdLong());
        var karma = entity.getKarmaEntity();
        if(karma.getLikeEmote() == null)
            return;
        boolean works = false;
        if(emote.equals(karma.getLikeEmote()))
            works = giveLike(entity, event.getMember(), session);
        else if(emote.equals(karma.getDislikeEmote()))
            works = giveDislike(entity, event.getMember(), session);
        session.close();
        if(!works)
            event.getReaction().removeReaction(event.getMember().getUser()).queue();
    }
    @SubscribeEvent
    public void onRemove(MessageReactionRemoveEvent event){
        if(disabledChannels.contains(event.getChannel().getIdLong()) || event.getMember() == null)
            return;
        var emote = event.getReactionEmote().getAsCodepoints();
        var session = Linwood.getInstance().getDatabase().getSessionFactory().openSession();
        var entity = Linwood.getInstance().getDatabase().getGuildById(session, event.getGuild().getIdLong());
        var karma = entity.getKarmaEntity();
        if(karma.getLikeEmote() == null)
            return;
        if(emote.equals(karma.getLikeEmote()))
            removeLike(entity, event.getMember(), session);
        else if(emote.equals(karma.getDislikeEmote()))
            removeDislike(entity, event.getMember(), session);
        session.close();
    }
    public boolean givingAction(GuildEntity entity, Member member){
        if(entity.getKarmaEntity().getMaxGiving() >= memberGivingHashMap.getOrDefault(member.getIdLong(), 0))
            return false;
        memberGivingHashMap.put(member.getIdLong(), memberGivingHashMap.getOrDefault(member.getIdLong(), 0) + 1);
        return true;
    }

    public boolean giveLike(GuildEntity entity, Member member, Session session){
        if(givingAction(entity, member))
            return false;
        var memberEntity = Linwood.getInstance().getDatabase().getMemberEntity(session, member);
        memberEntity.setLikes(memberEntity.getLikes() + 1);
        return true;
    }
    public void removeLike(GuildEntity entity, Member member, Session session){
        var memberEntity = Linwood.getInstance().getDatabase().getMemberEntity(session, member);
        memberEntity.setLikes(memberEntity.getLikes() - 1);
    }
    public boolean giveDislike(GuildEntity entity, Member member, Session session){
        if(givingAction(entity, member))
            return false;
        var memberEntity = Linwood.getInstance().getDatabase().getMemberEntity(session, member);
        return true;
    }
    public void removeDislike(GuildEntity entity, Member member, Session session){
        var memberEntity = Linwood.getInstance().getDatabase().getMemberEntity(session, member);
        memberEntity.setDislikes(memberEntity.getDislikes() - 1);
    }


    public boolean maybeReset(){
        if(!LocalDateTime.now().isAfter(ChronoLocalDateTime.from(lastReset)))
            return false;
        memberGivingHashMap.clear();
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
