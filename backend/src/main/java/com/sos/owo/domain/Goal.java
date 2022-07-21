package com.sos.owo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Goal {

    @Id
    @GeneratedValue
    @Column(name = "goal_id")
    private int id;

    @Column(name = "goal_type")
    @Enumerated(EnumType.ORDINAL)
    private Exercise exercise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "goal_hour")
    private int hour;

}
