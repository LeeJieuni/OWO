package com.sos.owo.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "record")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="record_id")
    private int recordId;

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
    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate recordDatetime;
//    private LocalDateTime recordDatetime;

    @Column(name="record_secret")
    private boolean recordSecret;

    @Column(name="record_exercise")
    @Enumerated(EnumType.STRING)
    private Exercise recordExercise; //meeting_room_type이 저장될것. 추후 목표시간을 달성했는지를 위함.

    public void updateRecordImg(RecordImg recordImg){
        this.recordImg = recordImg;
    }

//    @Override
//    public String toString() {
//        return "Record{" +
//                "recordId=" + recordId +
//                ", member=" + member.getNick() +
//                ", meetingRoom=" + meetingRoom.getName() +
//                ", recordHour=" + recordHour +
//                ", recordImg=" + recordImg +
//                ", recordMemo='" + recordMemo + '\'' +
//                ", recordDatetime=" + recordDatetime +
//                ", recordSecret=" + recordSecret +
//                ", recordExercise=" + recordExercise +
//                '}';
//    }
}