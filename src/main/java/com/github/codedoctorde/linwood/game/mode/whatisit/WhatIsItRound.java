package com.github.codedoctorde.linwood.game.mode.whatisit;

import com.github.codedoctorde.linwood.Main;
import net.dv8tion.jda.api.entities.Member;
import org.hibernate.Session;

import java.text.MessageFormat;
import java.util.*;

/**
 * @author CodeDoctorDE
 */
public class WhatIsItRound {
    private final long writerId;
    private String word;
    private final WhatIsIt whatIsIt;
    private Timer timer = new Timer();
    private final HashSet<Long> guesser = new HashSet<>();
    private int time = 120;

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
        }, 30 * 1000);
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
        whatIsIt.clearWantWriterMessage();
        stopTimer();
        time = 120;
        timer.schedule(new TimerTask() {

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
        int points = (guesser.size() < 5) ? 6 - guesser.size() : 1;
        whatIsIt.givePoints(member, points);
        return points;
    }

    public boolean isGuesser(Member member) {
        return guesser.stream().mapToLong(memberId -> memberId).anyMatch(memberId -> memberId == member.getIdLong());
    }

    public HashSet<Long> getGuesser() {
        return guesser;
    }

    public void stopTimer() {
        try{
            timer.cancel();
            timer = new Timer();
        }catch(Exception ignored){

        }
    }

    public void checkEverybody(Session session) {
        var last = new HashSet<>(guesser);
        whatIsIt.getWantWriter().forEach(last::remove);
        System.out.println("Want writer: " + whatIsIt.getWantWriter());
        System.out.println("Guesser: " + guesser);
        System.out.println("Last: " + last);
        if(last.size() <= 0){
            whatIsIt.getTextChannel().sendMessage(whatIsIt.getBundle(session).getString("Everybody")).queue();
            earlyStop();
        }
    }

    private void earlyStop() {
        time = 15;
    }
}
