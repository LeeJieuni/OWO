package com.sos.owo.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "profile_img")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImg {

    @Id
    @GeneratedValue
    @Column(name = "profile_img_id")
    private int id;

    @Column(name = "profile_img_name")
    private String fileName;

    @Column(name = "profile_img_oriname")
    private String fileOriName;

    @Column(name = "profile_img_fileurl")
    private String fileUrl;

    @Builder
    public ProfileImg(int id, String fileOriName, String fileName, String fileUrl) {
        this.id = id;
        this.fileOriName = fileOriName;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

    public void updateProfileImg(ProfileImg profileImg){
        this.fileOriName = profileImg.getFileOriName();
        this.fileName = profileImg.getFileName();
        this.fileUrl = profileImg.getFileUrl();
    }


}
