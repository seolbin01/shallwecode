package com.shallwecode.backend.user.domain.repository;

import com.shallwecode.backend.user.domain.aggregate.Noti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotiRepository extends JpaRepository<Noti, Long> {

    // UserInfo 엔티티의 userId를 기준으로 삭제
    void deleteByUser_UserId(Long userId);
}
