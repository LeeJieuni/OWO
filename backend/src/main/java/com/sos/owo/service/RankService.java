package com.sos.owo.service;

import com.sos.owo.domain.Member;
import com.sos.owo.domain.repository.MemberRepository;
import com.sos.owo.domain.repository.RecordRepository;
import com.sos.owo.dto.ResponseRankingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RankService {


    private final MemberRepository memberRepository;
    private final RecordRepository recordRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    // 랭킹 점수 반영
    @Transactional
    public void addRank(){
        if(redisTemplate.hasKey("ranking"))
            redisTemplate.delete("ranking");
        // 모든 유저 -> 기록들 합 저장
        List<Member> memberList = memberRepository.findAll();
        for(Member m:memberList){
            int sum = recordRepository.findYesterdaySum(m.getId());
            System.out.println(m.getId() +" "+sum);
            redisTemplate.opsForZSet().add("ranking", String.valueOf(m.getId()), sum);
        }
    }


    public List<ResponseRankingDto> getRankingList(){
        if(!redisTemplate.hasKey("ranking"))
            addRank();
        String key = "ranking";
        ZSetOperations<String, String> stringStringZSetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> typedTuples = stringStringZSetOperations.reverseRangeWithScores(key, 0, 2);

        List<ResponseRankingDto> rankingList = typedTuples.stream().map(ResponseRankingDto::convertToResponseRankingDto).collect(Collectors.toList());
        for(ResponseRankingDto dto:rankingList){
            Member findMember = memberRepository.findOne(dto.getMember_id());
            if(findMember.getNick() == null){
                String email = findMember.getEmail();
                dto.setName(email.split("@")[0]);
            } else {
                dto.setName(findMember.getNick());
            }
        }
        return rankingList;
    }

    public int getMyRank(int member_id){
        if(!redisTemplate.hasKey("ranking"))
            addRank();
        Long ranking=0L;
        Double ranking1 = redisTemplate.opsForZSet().score("ranking", String.valueOf(member_id));
        Set<String> ranking2 = redisTemplate.opsForZSet().reverseRangeByScore("ranking", ranking1, ranking1, 0, 1);
        for (String s : ranking2) {
            ranking = redisTemplate.opsForZSet().reverseRank("ranking", s);
        }
        return (int)(ranking+1);//index가 0부터 시작되어서 1 더해준다
    }
}
