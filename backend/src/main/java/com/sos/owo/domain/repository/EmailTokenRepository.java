package com.sos.owo.domain.repository;

import com.sos.owo.domain.EmailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
@Repository
public interface EmailTokenRepository extends JpaRepository<EmailToken, String> {
    // 이메일 토큰, 유효 정보를 저장할 Repository
    // 토큰 id를 찾다가 만료되지 않았으면 현재보다 이후에 만료되는 토큰의 정보를 가져옴
    Optional<EmailToken> findByIdAndExpirationDateAfterAndExpired(String emailTokenId, LocalDateTime now, boolean expired);
}
