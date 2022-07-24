package com.sos.owo.domain.repository;

import com.sos.owo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository2 extends JpaRepository<Member, Integer> {

    @Query()
    Optional<Member> findByEmail(String email);
}
