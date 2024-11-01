package com.shallwecode.backend.problem.domain.aggregate;

import jakarta.persistence.*;

@Entity
@Table(name = "problem")
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int problemId;
    private String title;
    private String content;
    private int problemLevel;
}
