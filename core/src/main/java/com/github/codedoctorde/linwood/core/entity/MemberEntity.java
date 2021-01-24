package com.github.codedoctorde.linwood.core.entity;

import com.github.codedoctorde.linwood.core.Linwood;
import org.hibernate.Session;

public abstract class MemberEntity extends GuildEntity {
    public GeneralMemberEntity getMember(Session session){
        return Linwood.getInstance().getDatabase().getGeneralMemberById(session, getGuildId(), getMemberId());
    }
    public abstract long getMemberId();
}
