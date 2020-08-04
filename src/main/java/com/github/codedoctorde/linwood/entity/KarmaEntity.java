package com.github.codedoctorde.linwood.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class KarmaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private int maxGiving = 3;
    private String likeEmote = null;
    private String dislikeEmote = null;
    private int constant = 5;
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "guildID", unique = true, nullable = false)
    @NotNull
    private GuildEntity guild;


    public GuildEntity getGuild() {
        return guild;
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
