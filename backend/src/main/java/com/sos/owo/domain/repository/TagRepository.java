package com.sos.owo.domain.repository;

import com.sos.owo.domain.Record;
import com.sos.owo.domain.Tag;
import com.sos.owo.dto.TagResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TagRepository {

    @PersistenceContext
    private final EntityManager em;

    //한 기록에 대한 태그 정보들 저장
    public void registTag(int recordId, List<String> tagList){
        Record findRecord = em.find(Record.class,recordId);

        for (String t:tagList) {
            Tag tag = new Tag();
            tag.setRecord(findRecord);
            tag.setTagContent(t);
            em.persist(tag);
        }
    }
    //태그id로 태그 정보 조회
    public TagResponseDto findTagOne(int tagId){
        Tag tag = em.find(Tag.class,tagId);
        TagResponseDto findTag = new TagResponseDto(tagId,tag.getRecord().getRecordId(),tag.getTagContent());
        return findTag;
    }

    //기록id에 따른(하나의 기록) 태그 이름들 리스트 조회
    public List<String> findTagNameList(int recordId){
        Record findRecord = em.find(Record.class,recordId);
        List<Tag> list = findRecord.getTags();
        List<String> tagNameList = new ArrayList<>();
        for (Tag item :list) {
            tagNameList.add(item.getTagContent());
        }
        return tagNameList;
    }

    //기록id에 따른(하나의 기록) 태그 정보들 리스트 조회
    public List<TagResponseDto> findTagList(int recordId){
        System.out.println("아아");
        Record findRecord = em.find(Record.class,recordId);
        List<Tag> list = findRecord.getTags();
        List<TagResponseDto> tagList = new ArrayList<>();
        for (Tag item :list) {
            tagList.add(new TagResponseDto(item.getTagId(),item.getRecord().getRecordId(),item.getTagContent()));
        }
        return tagList;
    }



}
