package com.sos.owo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "compete")
public class Compete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int competeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name="compete_score1")
    private int competeScore1;

    @Column(name="compete_score2")
    private int competeScore2;

    @Column(name="compete_score3")
    private int competeScore3;


}