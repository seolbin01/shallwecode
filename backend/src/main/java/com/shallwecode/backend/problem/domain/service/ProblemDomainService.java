package com.shallwecode.backend.problem.domain.service;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shallwecode.backend.problem.application.dto.FindMyProblemResDTO;
import com.shallwecode.backend.problem.application.dto.ProblemReqDTO;
import com.shallwecode.backend.problem.application.dto.ProblemResDTO;
import com.shallwecode.backend.problem.application.dto.ProblemResListDTO;
import com.shallwecode.backend.problem.domain.aggregate.Problem;
import com.shallwecode.backend.problem.domain.aggregate.QProblem;
import com.shallwecode.backend.problem.domain.aggregate.QTry;
import com.shallwecode.backend.problem.domain.aggregate.QTestcase;
import com.shallwecode.backend.problem.domain.aggregate.Testcase;
import com.shallwecode.backend.problem.domain.repository.ProblemRepository;
import com.shallwecode.backend.problem.domain.repository.TestcaseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemDomainService {

    private final ProblemRepository repository;
    private final TestcaseRepository testCaseRepository;
    private final ModelMapper modelMapper;
    private final JPAQueryFactory queryFactory;

    @Transactional
    public void saveProblem(ProblemReqDTO newProblem) {

        Problem problem = modelMapper.map(newProblem, Problem.class);
        repository.save(problem);

        // 연관된 테스트 케이스 저장
        newProblem.getTestcases().forEach(testcaseReqDTO -> {
            Testcase testcase = modelMapper.map(testcaseReqDTO, Testcase.class);
            testcase.updateProblemId(problem.getProblemId()); // 문제 ID 설정
            testCaseRepository.save(testcase);
        });
    }

    @Transactional
    public void updateProblem(Long id, ProblemReqDTO updateProblem) {

        // 문제를 조회하고 제목, 내용, 난이도 업데이트
        Problem foundProblem = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 id에 맞는 문제가 존재하지 않습니다."));
        foundProblem.updateProblemTitle(updateProblem.getTitle());
        foundProblem.updateProblemContent(updateProblem.getContent());
        foundProblem.updateProblemProblemLevel(updateProblem.getProblemLevel());

        // 기존 테스트 케이스 삭제 후 새로운 테스트 케이스 추가
        testCaseRepository.deleteByProblemId(id);
        updateProblem.getTestcases().forEach(testcaseReqDTO -> {
            Testcase testcase = modelMapper.map(testcaseReqDTO, Testcase.class);
            testcase.updateProblemId(foundProblem.getProblemId()); // 문제 ID 설정
            testCaseRepository.save(testcase);
        });
    }

    @Transactional
    public void deleteProblem(Long problemId) {

        // 문제와 연관된 테스트 케이스 모두 삭제 후 문제 삭제
        testCaseRepository.deleteByProblemId(problemId);
        repository.deleteById(problemId);
    }

    public List<ProblemResDTO> selectOneProblem(Long problemId) {

        QProblem qProblem = QProblem.problem;
        QTestcase qTestcase = QTestcase.testcase;

        return queryFactory.select(Projections.constructor(ProblemResDTO.class,
                qProblem.problemId,
                qProblem.title,
                qProblem.content,
                qProblem.problemLevel,
                qTestcase.testcaseId,
                qTestcase.input,
                qTestcase.output))
                .from(qProblem)
                .leftJoin(qTestcase).on(qTestcase.problemId.eq(qProblem.problemId))
                .where(qProblem.problemId.eq(problemId))
                .fetch();
    }

    public List<FindMyProblemResDTO> findAllMyProblem(Long userId) {

        QProblem qProblem = QProblem.problem;
        QTry qTry = QTry.try$;

        return queryFactory
                .select(Projections.constructor(FindMyProblemResDTO.class,
                        qProblem.problemId,
                        qProblem.title,
                        qProblem.problemLevel,
                        QTry.try$.isSolved
                                .when(true).then(1)
                                .otherwise(0)
                                .max()))
                .from(qTry)
                .join(qProblem).on(qTry.problemId.eq(qProblem.problemId))
                .where(qTry.userId.eq(userId))
                .groupBy(qProblem.problemId)
                .orderBy(qTry.createdAt.asc())
                .fetch();
    }

    /* 문제 목록 조회 기능 */
    public List<ProblemResListDTO> selectProblemList() {

        QProblem qProblem = QProblem.problem;

        return queryFactory.select(Projections.constructor(ProblemResListDTO.class,
                qProblem.problemId,
                qProblem.title,
                qProblem.content,
                qProblem.problemLevel))
                .from(qProblem)
                .fetch();
    }
}
