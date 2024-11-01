package com.shallwecode.backend.problem.domain.aggregate;

import jakarta.persistence.*;

@Entity
@Table(name = "testcase")
public class Testcase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long testcaseId;
    private long problemId;
    private String input;
    private String output;
}
