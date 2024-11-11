package com.shallwecode.backend.problem.domain.repository;

import com.shallwecode.backend.problem.domain.aggregate.Testcase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestcaseRepository extends JpaRepository<Testcase, Long> {

    // 특정 problemId에 해당하는 모든 테스트 케이스 삭제
    void deleteByProblemId(Long id);
    Optional<Testcase> findByProblemId(Long id);
}
