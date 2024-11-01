package com.shallwecode.backend.problem.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ProblemResDTO {
    private long problemId;
    private String title;
    private String content;
    private int problemLevel;
    private String input;
    private String output;
}
