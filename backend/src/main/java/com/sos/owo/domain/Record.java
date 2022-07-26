package com.sos.owo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "record")
public class Record {

    @Id
    @GeneratedValue
    @Column(name="record_id")
    private int record_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_room_id")
    private MeetingRoom meetingRoom;

    @OneToMany(mappedBy = "record",cascade = CascadeType.ALL)
    private List<Tag> tags = new ArrayList<>();

    @Column(name="record_hour")
    private int recordHour;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "record_img_id")
    private RecordImg recordImg;

    @Column(name="record_memo")
    private String recordMemo;

    @Column(name="record_datetime")
    private LocalDateTime recordDatetime;

    @Column(name="record_secret")
    private int record_secret;
}