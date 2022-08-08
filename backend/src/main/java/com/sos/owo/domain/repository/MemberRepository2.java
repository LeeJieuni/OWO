package com.sos.owo.domain.repository;

import com.sos.owo.domain.Member;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository2 extends JpaRepository<Member, Integer> {

    Optional<Member> findByEmail(String email);

    @Query(value = "select b.rank from (SELECT member_id, @curRank \\:= @curRank + 1 AS rank FROM member m, (SELECT @curRank \\:= 0) m ORDER BY  member_point) b where b.member_id = :member_id", nativeQuery = true)
    int findRanking(@Param("member_id")int member_id) throws Exception;

    @Query(value = "select count(*) from member", nativeQuery = true)
    int findMemberCnt() throws Exception;
}
