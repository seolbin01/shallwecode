package com.shallwecode.backend.problem.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;

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
