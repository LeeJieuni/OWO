package com.sos.owo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "profile_img")
public class ProfileImg {

    @Id
    @GeneratedValue
    @Column(name = "profile_img_id")
    private int id;

    @Column(name = "profile_img_name")
    private String fileName;

    @Column(name = "profile_img_oriname")
    private String FileOriName;

    @Column(name = "profile_img_fileurl")
    private String FileUrl;




}
