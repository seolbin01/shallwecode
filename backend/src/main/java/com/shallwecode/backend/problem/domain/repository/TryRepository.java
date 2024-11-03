package com.shallwecode.backend.problem.domain.repository;

import com.shallwecode.backend.problem.domain.aggregate.Try;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TryRepository extends JpaRepository<Try, Long> {

}
