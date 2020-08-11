package com.github.codedoctorde.linwood.entity;

import javax.persistence.*;

/**
 * @author CodeDoctorDE
 */
@Entity
@Table
public class DeleteChannelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private long channelId;
}
