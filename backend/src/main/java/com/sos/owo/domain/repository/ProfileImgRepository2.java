package com.sos.owo.domain.repository;

import com.sos.owo.domain.Member;
import com.sos.owo.domain.ProfileImg;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProfileImgRepository2 {

    @PersistenceContext
    private final EntityManager em;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    ProfileImgRepository profileImgRepository;

    public int saveImg(int memberId,String fileOriName, String fileUrl){
        Member findMember = memberRepository.findOne(memberId);
        if(findMember == null){
            throw new NullPointerException();
        }
        ProfileImg profileImg = new ProfileImg();
        profileImg.setFileOriName(fileOriName);
        profileImg.setFileUrl(fileUrl.getBytes()); //String으로 온것 byte[]로 저장

        if(findMember.getProfileImg() != null){
            ProfileImg findProfileImg = findMember.getProfileImg();
            em.remove(findProfileImg);
        }
//        else {
////            findProfileImg.updateProfileImg(profileImg);
//        }
        profileImgRepository.save(profileImg);
        findMember.updateProfieImg(profileImg);
        return profileImg.getId();
    }

    public ProfileImg getImg(int memberId){
        Member findMember = memberRepository.findOne(memberId);
        ProfileImg profileImg = findMember.getProfileImg();
        return profileImg;
    }

}
