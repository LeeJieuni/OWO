package com.sos.owo.domain.repository;

import com.sos.owo.domain.Member;
import com.sos.owo.domain.ProfileImg;
import com.sos.owo.dto.FileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProfileImgRepository2 {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired ProfileImgRepository profileImgRepository;

    public int saveImg(int memberId,String fileOriName, String fileUrl){
        Member findMember = memberRepository.findOne(memberId);
        ProfileImg profileImg = new ProfileImg();
        profileImg.setFileOriName(fileOriName);
        profileImg.setFileUrl(fileUrl.getBytes()); //String으로 온것 byte[]로 저장

        if(findMember.getProfileImg() == null){

            profileImgRepository.save(profileImg);
        } else {
            ProfileImg findProfileImg = findMember.getProfileImg();
            findProfileImg.updateProfileImg(profileImg);
        }
        findMember.updateProfieImg(profileImg);
        return profileImg.getId();
    }

    public ProfileImg getImg(int memberId){
        Member findMember = memberRepository.findOne(memberId);
        ProfileImg profileImg = findMember.getProfileImg();
        return profileImg;
    }

}
