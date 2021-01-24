package com.github.codedoctorde.linwood.karma;

import com.github.codedoctorde.linwood.core.module.LinwoodModule;
import com.github.codedoctorde.linwood.karma.entity.KarmaMemberEntity;
import org.hibernate.Session;

/**
 * @author CodeDoctorDE
 */
public class KarmaAddon extends LinwoodModule {
    private static KarmaAddon instance;

    public static KarmaAddon getInstance() {
        return instance;
    }

    public KarmaAddon() {
        super("main");
        instance = this;
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public KarmaMemberEntity[] getKarmaLeaderboard(Session session){
        return getKarmaLeaderboard(session, null);
    }
    public KarmaMemberEntity[] getKarmaLeaderboard(Session session, Long guildId){
        return getKarmaLeaderboard(session, guildId, 20);
    }

    public KarmaMemberEntity[] getKarmaLeaderboard(Session session, int maxResults){
        return getKarmaLeaderboard(session, null, maxResults);
    }

    public KarmaMemberEntity[] getKarmaLeaderboard(Session session, Long guildId, int maxResults){
// Create CriteriaBuilder
        var builder = session.getCriteriaBuilder();

// Create CriteriaQuery
        var cq = builder.createQuery(KarmaMemberEntity.class);
        var member = cq.from(KarmaMemberEntity.class);
        var all = cq.select(member);
        if(guildId != null)
            all.where(builder.equal(member.get("guildId"), guildId));
        all.orderBy(builder.desc(member.get("likes")), builder.asc(member.get("dislikes")));
        var allQuery = session.createQuery(all);
        allQuery.setMaxResults(maxResults);
        return allQuery.getResultList().toArray(new KarmaMemberEntity[0]);
    }
}
