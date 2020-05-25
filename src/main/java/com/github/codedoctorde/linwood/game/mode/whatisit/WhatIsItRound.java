package com.github.codedoctorde.linwood.game.mode.whatisit;

import net.dv8tion.jda.api.entities.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CodeDoctorDE
 */
public class WhatIsItRound {
    private long writerId;
    private String word;
    private WhatIsIt whatIsIt;
    private final List<Long> guesser = new ArrayList<>();
    public WhatIsItRound(long writerId, String word, WhatIsIt whatIsIt){
        this.writerId = writerId;
        this.word = word;
        this.whatIsIt = whatIsIt;
    }

    public String getWord() {
        return word;
    }

    public long getWriterId() {
        return writerId;
    }
    public Member getWriter(){
        return whatIsIt.getGame().getGuild().getMemberById(writerId);
    }
}
