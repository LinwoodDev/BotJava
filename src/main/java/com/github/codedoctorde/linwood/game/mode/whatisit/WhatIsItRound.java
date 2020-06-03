package com.github.codedoctorde.linwood.game.mode.whatisit;

import net.dv8tion.jda.api.entities.Member;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author CodeDoctorDE
 */
public class WhatIsItRound {
    private final long writerId;
    private String word;
    private final WhatIsIt whatIsIt;
    private final Timer timer = new Timer();
    private final List<Long> guesser = new ArrayList<>();

    public WhatIsItRound(long writerId, WhatIsIt whatIsIt) {
        this.writerId = writerId;
        this.whatIsIt = whatIsIt;
    }

    public void inputWriter(){

    }

    public String getWord() {
        return word;
    }

    public long getWriterId() {
        return writerId;
    }

    public Member getWriter() {
        return whatIsIt.getGame().getGuild().getMemberById(writerId);
    }

    public void startRound(Session session) {
        timer.schedule(new TimerTask() {
            int time = 180;

            @Override
            public void run() {
                if (time <= 0 || whatIsIt.getTextChannel() == null) {
                    timer.cancel();
                    whatIsIt.finishRound();
                } else
                    switch (time) {
                        case 120:
                        case 60:
                            break;
                        case 30:
                        case 20:
                        case 10:
                        case 5:
                        case 4:
                        case 3:
                        case 2:
                        case 1:
                    }
                time--;
            }
        }, 1000, 1000);
    }

    /**
     * Points:
     * 1st: 5 points
     * 2nd: 4 points
     * 3rd: 3 points
     * 4th: 2 points
     * 5th and under: 1 point
     *
     * @param member Current guesser
     */
    public int guessCorrectly(Member member) {
        guesser.add(member.getIdLong());
        int points = (guesser.size() < 5) ? 5 - guesser.size() : 1;
        whatIsIt.givePoints(member, points);
        return points;
    }

    public boolean isGuesser(Member member) {
        return guesser.stream().mapToLong(memberId -> memberId).anyMatch(memberId -> memberId == member.getIdLong());
    }

    public List<Long> getGuesser() {
        return guesser;
    }

    public void stopTimer() {
        timer.cancel();
    }
}
