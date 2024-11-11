package com.shallwecode.backend.problem.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProblemDTO {
    private Long problemId;
    private String title;
    private String content;
    private int problemLevel;
}
