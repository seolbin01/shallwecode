package com.shallwecode.backend.user.domain.repository;

import com.shallwecode.backend.user.domain.aggregate.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserInfo, Long> {
    // 사용자 이메일 중복 여부 확인
    boolean existsByEmail(String email);

    // 특정 유저 찾기
    Optional<UserInfo> findByEmail(String email);
}
