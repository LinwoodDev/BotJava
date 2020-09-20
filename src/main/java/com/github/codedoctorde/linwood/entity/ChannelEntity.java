package com.github.codedoctorde.linwood.entity;

import javax.persistence.*;

/**
 * @author CodeDoctorDE
 */
@Entity
@Table
public class ChannelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @OneToOne
    private TeamEntity team;
    private String name;
    private VisibilityLevel visibility = VisibilityLevel.PUBLIC;

    public Long getId() {
        return id;
    }

    public TeamEntity getTeam() {
        return team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VisibilityLevel getVisibility() {
        return visibility;
    }

    public void setVisibility(VisibilityLevel visibility) {
        this.visibility = visibility;
    }
}
