package com.github.codedoctorde.linwood.karma.entity;

import com.github.codedoctorde.linwood.core.entity.GuildEntity;
import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "karma")
public class KarmaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private int maxGiving = 3;
    private String likeEmote = null;
    private String dislikeEmote = null;
    private int constant = 1;
    @Column(name="guildID", unique = true, nullable = false)
    @Id
    private int guildId;

    public int getGuildId() {
        return guildId;
    }

    public String getLikeEmote() {
        return likeEmote;
    }

    public void setLikeEmote(String likeEmote) {
        this.likeEmote = likeEmote;
    }

    public String getDislikeEmote() {
        return dislikeEmote;
    }

    public void setDislikeEmote(String dislikeEmote) {
        this.dislikeEmote = dislikeEmote;
    }

    public int getConstant() {
        return constant;
    }

    public void setConstant(int constant) {
        this.constant = constant;
    }

    public int getMaxGiving() {
        return maxGiving;
    }

    public void setMaxGiving(int maxGiving) {
        this.maxGiving = maxGiving;
    }
}
