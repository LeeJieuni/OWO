package com.sos.owo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "member")
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private int id;

    @Column(name = "member_email")
    private String email;

    @Column(name ="member_pw")
    private String pw;

    @Column(name="member_nick")
    private String nick;

    @Column(name = "member_gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "member_height")
    private double height;

    @Column(name = "member_weight")
    private double weight;

    @Column(name = "member_activity_num")
    private int activityNum;

    @Column(name = "member_activity_hour")
    private int activityHour;

    @Column(name = "member_img")
    private String img;

    @Column(name = "member_point")
    private int point;

    @Column(name = "member_secret")
    private int secret;

    @Column(name = "member_token")
    private String token;

    @Column(name = "member_exp")
    private int exp;

    @Column(name = "member_joindate")
    private LocalDateTime joindate;

    @OneToMany(mappedBy = "goal")
    private List<Goal> goalList = new ArrayList<>();

    @OneToMany(mappedBy = "meeting_oauth")
    private List<MeetingOauth> meetingOauthList = new ArrayList<>();


}
