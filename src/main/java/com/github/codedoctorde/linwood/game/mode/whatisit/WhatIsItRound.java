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

    /**
     * Points:
     * 1st: 5 points
     * 2nd: 4 points
     * 3rd: 3 points
     * 4th: 2 points
     * 5th and under: 1 point
     * @param member Current guesser
     */
    public int guessCorrectly(Member member){
        guesser.add(member.getIdLong());
        int points = (guesser.size() < 5) ? 5 - guesser.size(): 1;
        whatIsIt.addPoint(member, points);
        return points;
    }
    public boolean isGuesser(Member member){
        return guesser.stream().mapToLong(memberId -> memberId).anyMatch(memberId -> memberId == member.getIdLong());
    }
}
