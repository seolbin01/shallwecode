package com.shallwecode.backend.problem.application.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ProblemResDTO {
    private Long problemId;
    private String title;
    private String content;
    private int problemLevel;
    private Long testcaseId;
    private String input;
    private String output;
}
