package com.sos.owo.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "goal")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_id")
    private int id;

    
    @Column(name = "goal_type")
    @Enumerated(EnumType.STRING)
    private Exercise exercise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "goal_hour")
    private int hour;

    public Goal(Exercise exercise, Member member, int hour) {
        this.exercise = exercise;
        this.member = member;
        this.hour = hour;
    }



}