package com.shallwecode.backend.user.domain.repository;

import com.shallwecode.backend.user.domain.aggregate.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserInfo, Long> {
    // 사용자 이메일 중복 여부 확인
    boolean existsByEmail(String email);
}
