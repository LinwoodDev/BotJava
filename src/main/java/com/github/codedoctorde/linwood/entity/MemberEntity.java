package com.github.codedoctorde.linwood.entity;

/**
 * @author CodeDoctorDE
 */
public class MemberEntity {
    private long memberId;
    private String locale = null;
    private int points;
    private int karma;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public int getKarma() {
        return karma;
    }

    public void setKarma(int karma) {
        this.karma = karma;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public long getMemberId() {
        return memberId;
    }
}
