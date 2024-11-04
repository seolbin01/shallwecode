package com.shallwecode.backend.problem.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "testcase")
@Getter
public class Testcase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testcaseId;

    private String input;
    private String output;

    @ManyToOne
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    // 양방향 관계에서 Problem 엔티티와 연결을 설정하는 메서드
    public void setProblem(Problem problem) {
        this.problem = problem;
    }



}
