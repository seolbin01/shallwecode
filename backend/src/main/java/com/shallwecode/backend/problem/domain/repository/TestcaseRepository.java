package com.shallwecode.backend.problem.domain.repository;

import com.shallwecode.backend.problem.domain.aggregate.Problem;
import com.shallwecode.backend.problem.domain.aggregate.Testcase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestcaseRepository extends JpaRepository<Testcase, Long> {

    // 특정 problemId에 해당하는 모든 테스트 케이스 삭제
    void deleteByProblem_ProblemId(Long id);
}
