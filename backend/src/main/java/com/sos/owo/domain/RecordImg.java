package com.sos.owo.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "record_img")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_img_id")
    private int id;

    @Column(name = "record_img_name")
    private String fileName;

    @Column(name = "record_img_oriname")
    private String fileOriName;

    @Column(name = "record_img_fileurl")
    private String fileUrl;

    @Builder
    public RecordImg(int id, String fileOriName, String fileName, String fileUrl){
        this.id = id;
        this.fileOriName = fileOriName;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

    public void updateRecordImg(RecordImg recordImg){
        this.fileOriName = recordImg.getFileOriName();
        this.fileName = recordImg.getFileName();
        this.fileUrl = recordImg.getFileUrl();
    }
}
