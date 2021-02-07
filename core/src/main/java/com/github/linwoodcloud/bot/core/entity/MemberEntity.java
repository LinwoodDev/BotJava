package com.github.linwoodcloud.bot.core.entity;

import com.github.linwoodcloud.bot.core.Linwood;
import org.hibernate.Session;

public abstract class MemberEntity extends GuildEntity {
    public GeneralMemberEntity getMember(Session session){
        return Linwood.getInstance().getDatabase().getGeneralMemberById(session, getGuildId(), getMemberId());
    }
    public abstract long getMemberId();
}
