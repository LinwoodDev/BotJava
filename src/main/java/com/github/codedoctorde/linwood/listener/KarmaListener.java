package com.github.codedoctorde.linwood.listener;

import com.github.codedoctorde.linwood.Linwood;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
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
        var game = Linwood.getInstance().getSingleApplicationManager().getGame(event.getGuild().getIdLong());
        if(disabledChannels.contains(event.getChannel().getIdLong()))
            return;
        var emote = event.getReactionEmote().getAsCodepoints();
        var session = Linwood.getInstance().getDatabase().getSessionFactory().openSession();
        var entity = Linwood.getInstance().getDatabase().getGuildById(session, event.getGuild().getIdLong());
        var karma = entity.getKarmaEntity();
        if(karma.getLikeEmote() == null)
            return;
        if(emote.equals(karma.getLikeEmote()))
            giveLike(entity, event.getMember(), session);
        else if(emote.equals(karma.getDislikeEmote()))
            giveDislike(entity, event.getMember(), session);
        session.close();
    }

    public void giveLike(GuildEntity entity, Member member, Session session){
        if(givingAction(entity, member))
            removeDislike(entity, member, session);
    }
    public boolean givingAction(GuildEntity entity, Member member){
        if(entity.getKarmaEntity().getMaxGiving() >= memberGivingHashMap.getOrDefault(member.getIdLong(), 0))
            return false;
        memberGivingHashMap.put(member.getIdLong(), memberGivingHashMap.getOrDefault(member.getIdLong(), 0) + 1);
        return true;
    }
    public void removeLike(GuildEntity entity, Member member, Session session){

    }
    public void giveDislike(GuildEntity entity, Member member, Session session){
        if(givingAction(entity, member))
            removeLike(entity, member, session);
    }
    public void removeDislike(GuildEntity entity, Member member, Session session){

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
