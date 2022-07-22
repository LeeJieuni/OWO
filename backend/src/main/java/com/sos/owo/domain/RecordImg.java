package com.sos.owo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "record_img")
public class RecordImg {
    @Id
    @GeneratedValue
    @Column(name = "record_img_id")
    private int id;

    @Column(name = "record_img_name")
    private String fileName;

    @Column(name = "record_img_oriname")
    private String FileOriName;

    @Column(name = "record_img_fileurl")
    private String FileUrl;
}
