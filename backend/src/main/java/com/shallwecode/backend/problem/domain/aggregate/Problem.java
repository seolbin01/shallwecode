package com.shallwecode.backend.problem.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "problem")
@Getter
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long problemId;

    private String title;
    private String content;
    private int problemLevel;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Testcase> testCases = new ArrayList<>();

    // 테스트 케이스 추가 메서드
    public void addTestCase(Testcase testCase) {
        testCases.add(testCase);
        testCase.setProblem(this); // 양방향 연관 관계 설정
    }

    public void removeTestCase(Testcase testCase) {
        testCases.remove(testCase);
        testCase.setProblem(null);
    }



    public void updateProblemTitle(String title) {
        this.title = title;
    }

    public void updateProblemContent(String content) {
        this.content = content;
    }

    public void updateProblemProblemLevel(int problemLevel) {
        this.problemLevel = problemLevel;
    }



}
