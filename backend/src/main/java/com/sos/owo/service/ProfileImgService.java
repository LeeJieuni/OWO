package com.sos.owo.service;

import com.sos.owo.domain.Member;
import com.sos.owo.domain.ProfileImg;
import com.sos.owo.domain.repository.MemberRepository;
import com.sos.owo.domain.repository.ProfileImgRepository;
import com.sos.owo.domain.repository.ProfileImgRepository2;
import com.sos.owo.dto.FileDto;
import com.sos.owo.dto.MemberProfileImgDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileImgService {

    @Autowired
    private ProfileImgRepository2 profileImgRepository2;
    @Autowired
    private MemberRepository memberRepository;

//    @Transactional
//    public FileDto saveFile(int memberId, String fileOriName, String fileName, String fileUrl) throws IllegalStateException {
//        Member findMember = memberRepository.findOne(memberId);
//        FileDto fileDto = new FileDto();
//        fileDto.setFileName(fileOriName);
//        fileDto.setFileOriName(fileName);
//        fileDto.setFileUrl(fileUrl);
//        ProfileImg profileImg = fileDto.toEntity();
//        if(findMember.getProfileImg() == null){
//            profileImgRepository.save(profileImg);
//        } else {
//            ProfileImg findProfileImg = findMember.getProfileImg();
//            findProfileImg.updateProfileImg(profileImg);
//        }
//        findMember.updateProfieImg(profileImg);
//
//        return fileDto;
//    }

//    @Transactional
//    public FileDto getFile(int memberId){
//        Member findMember = memberRepository.findOne(memberId);
//        ProfileImg profileImg = findMember.getProfileImg();
//        if(profileImg == null) return null;
//        FileDto fileDto = FileDto.builder()
//                .id(profileImg.getId())
//                .fileOriName(profileImg.getFileOriName())
//                .fileName(profileImg.getFileName())
//                .fileUrl(profileImg.getFileUrl())
//                .build();
//        return fileDto;
//    }

    @Transactional
    public int saveImg(int memberId,String fileOriName, String fileUrl){
        return profileImgRepository2.saveImg(memberId, fileOriName, fileUrl);
    }

    @Transactional
    public ProfileImg getImg(int memberId){
        return profileImgRepository2.getImg(memberId);
    }
}
