package com.shallwecode.backend.user.domain.repository;

import com.shallwecode.backend.user.domain.aggregate.Noti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotiRepository extends JpaRepository<Noti, Long> {
}
