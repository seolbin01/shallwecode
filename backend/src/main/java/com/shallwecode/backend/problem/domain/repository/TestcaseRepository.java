package com.shallwecode.backend.problem.domain.repository;

import com.shallwecode.backend.problem.domain.aggregate.Testcase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestcaseRepository extends JpaRepository<Testcase, Long> {

}
