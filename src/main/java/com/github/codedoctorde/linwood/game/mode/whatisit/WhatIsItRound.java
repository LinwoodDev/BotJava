package com.github.codedoctorde.linwood.game.mode.whatisit;

import com.github.codedoctorde.linwood.Main;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.hibernate.Session;

import java.text.MessageFormat;
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
    private Timer timer = new Timer();
    private final List<Long> guesser = new ArrayList<>();

    public WhatIsItRound(long writerId, WhatIsIt whatIsIt) {
        this.writerId = writerId;
        this.whatIsIt = whatIsIt;
    }

    public void inputWriter(){
        whatIsIt.getGame().getGuild().retrieveMemberById(writerId).queue(member ->
                member.getUser().openPrivateChannel().queue(privateChannel -> {
                    var session = Main.getInstance().getDatabase().getSessionFactory().openSession();
                    privateChannel.sendMessage(whatIsIt.getBundle(session).getString("Input")).queue();
                    session.close();
                })
                );
        stopTimer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                var session = Main.getInstance().getDatabase().getSessionFactory().openSession();
                whatIsIt.cancelRound(session);
                session.close();
            }
        }, 10000);
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

    public void startRound(String word) {
        this.word = word;
        stopTimer();
        timer.schedule(new TimerTask() {
            int time = 180;

            @Override
            public void run() {
                var session = Main.getInstance().getDatabase().getSessionFactory().openSession();
                var bundle = whatIsIt.getBundle(session);
                if (time <= 0 || whatIsIt.getTextChannel() == null) {
                    timer.cancel();
                    whatIsIt.finishRound(session);
                } else {
                    String message = null;
                    switch (time) {
                        case 180:
                        case 120:
                            message = MessageFormat.format(bundle.getString("CountdownMinutes"), time / 60);
                            break;
                        case 60:
                            message = bundle.getString("CountdownMinute");
                            break;
                        case 30:
                        case 20:
                        case 10:
                        case 5:
                        case 4:
                        case 3:
                        case 2:
                            message = MessageFormat.format(bundle.getString("CountdownSeconds"), time);
                            break;
                        case 1:
                            message = bundle.getString("CountdownSecond");
                    }
                    if(message != null)
                        whatIsIt.getTextChannel().sendMessage(message).queue();
                    time--;
                }
            }
        }, 1000, 1000);
    }
    public void stopRound(){
        stopTimer();
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
        try{
            timer.cancel();
            timer = new Timer();
        }catch(Exception ignored){

        }
    }
}
