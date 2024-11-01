package com.shallwecode.backend.problem.domain.repository;

import com.shallwecode.backend.problem.domain.aggregate.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

}
