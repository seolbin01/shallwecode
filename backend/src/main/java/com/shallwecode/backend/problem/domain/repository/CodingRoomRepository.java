package com.shallwecode.backend.problem.domain.repository;

import com.shallwecode.backend.problem.domain.aggregate.CodingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodingRoomRepository extends JpaRepository<CodingRoom, Long> {

}
