package com.sos.owo.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "meeting_room")
public class MeetingRoom {
    @Id @GeneratedValue
    @Column(name="meeting_room_id")
    private int id;

    @Column(name="meeting_room_name",nullable = false)
    private String name;

    @Column(name="meeting_room_secret")
    private int secret;

    @Column(name="meeting_room_mode")
    private int mode;

    @Column(name="meeting_room_type")
    private int type;

    @Column(name="meeting_room_link")
    private String link;

    @Column(name = "meeting_room_open_date")
    private LocalDateTime openDate;

    @Column(name = "meeting_room_start_date")
    private LocalDateTime startDate;

    @Column(name = "meeting_room_end_date")
    private LocalDateTime endDate;

    @Column(name = "meeting_room_password")
    private int password;

    @Column(name = "meeting_room_reg_dt")
    private LocalDateTime regDt;

    @Column(name = "meeting_room_img")
    private String img;

    @Column(name = "meeting_room_manager",nullable = false)
    private int manager;

    @OneToMany
    private List<MeetingOauth> oauthList = new ArrayList<>();



}