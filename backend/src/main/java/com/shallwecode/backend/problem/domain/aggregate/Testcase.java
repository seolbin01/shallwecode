package com.shallwecode.backend.problem.domain.aggregate;

import jakarta.persistence.*;

@Entity
@Table(name = "testcase")
public class Testcase {
    @Id
    @Column(name="testcase_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long testcaseId;

    @Column(name="problem_id")
    private long problemId;

    @Column(name="input")
    private String input;

    @Column(name="output")
    private String output;
}
