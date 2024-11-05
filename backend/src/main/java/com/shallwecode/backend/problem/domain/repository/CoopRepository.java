package com.shallwecode.backend.problem.domain.repository;

import com.shallwecode.backend.problem.domain.aggregate.Coop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoopRepository extends JpaRepository<Coop, Long> {

    // 특정 codingRoomId에 해당하는 모든 테스트 케이스 삭제
    void deleteByCodingRoomId(Long id);

}
