package com.shallwecode.backend.problem.domain.aggregate;

import jakarta.persistence.*;

@Entity
@Table(name = "problem")
public class Problem {
    @Id
    @Column(name = "problem_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int problemId;

    @Column(name="title")
    private String title;

    @Column(name="content")
    private String content;

    @Column(name="problem_level")
    private int problemLevel;
}
