package com.sos.owo.domain.repository;

import com.sos.owo.domain.Record;
import com.sos.owo.domain.Tag;
import com.sos.owo.dto.TagResponseDto;
import com.sos.owo.error.Exception.custom.SomethingNotFoundException;
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
        /* 앞에서 findRecord가 null이라면 이미 예외처리 됐을 테니 여기선 생략 */
        if (tagList.isEmpty()) return; /* 태그가 없는 경우 그냥 리턴 */
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
//        if(tag == null) throw new SomethingNotFoundException("tag(id:"+tagId+")");
        TagResponseDto findTag = null;
        if(tag != null){
            findTag = new TagResponseDto(tagId,tag.getRecord().getRecordId(),tag.getTagContent());
        }
        return findTag;
    }

    //기록id에 따른(하나의 기록) 태그 이름들 리스트 조회
    public List<String> findTagNameList(int recordId){
        Record findRecord = em.find(Record.class,recordId);
        if(findRecord == null) throw new SomethingNotFoundException("record(id:"+recordId+")");

        List<Tag> list = findRecord.getTags();
        List<String> tagNameList = new ArrayList<>();
        if(!list.isEmpty()) {
            for (Tag item :list) {
                tagNameList.add(item.getTagContent());
            }
        }

        return tagNameList;
    }

    //기록id에 따른(하나의 기록) 태그 정보들 리스트 조회
    public List<TagResponseDto> findTagList(int recordId){
        Record findRecord = em.find(Record.class,recordId);
        if(findRecord == null) throw new SomethingNotFoundException("record(id:"+recordId+")");

        List<Tag> list = findRecord.getTags();
        List<TagResponseDto> tagList = new ArrayList<>();
        if(!list.isEmpty()) {
            for (Tag item :list) {
                tagList.add(new TagResponseDto(item.getTagId(),item.getRecord().getRecordId(),item.getTagContent()));
            }
        }
//        throw new SomethingNotFoundException("record(id:"+recordId+")'s tagList "); //if(list.isEmpty())
        return tagList;
    }



}
