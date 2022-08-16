package com.sos.owo.domain.repository;

import com.sos.owo.domain.Member;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository2 extends JpaRepository<Member, Integer> {

    Optional<Member> findByEmail(String email);

    @Query(value = "select rm.ranking from (select m.member_id, rank() over (order by m.member_point desc) as ranking from (select * from member m where m.member_enable = 1) m) rm where rm.member_id = :member_id", nativeQuery = true)
    int findRanking(@Param("member_id")int member_id) throws Exception;

    @Query(value = "select count(*) from member m where m.member_enable = 1", nativeQuery = true)
    int findMemberCnt() throws Exception;
}
