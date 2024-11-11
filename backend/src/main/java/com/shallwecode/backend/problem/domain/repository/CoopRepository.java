package com.shallwecode.backend.problem.domain.repository;

import com.shallwecode.backend.problem.domain.aggregate.Coop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoopRepository extends JpaRepository<Coop, Long> {

    void deleteByCodingRoomId(Long id);
    Optional<Coop> findByCodingRoomIdAndUserId(Long codingRoomId, Long userId);
    Optional<Coop> findByCodingRoomIdAndCoopId(Long codingRoomId, Long coopId);
    void deleteByUserId(Long userId);
    Optional<Coop> findByUserId(Long userId);
}
