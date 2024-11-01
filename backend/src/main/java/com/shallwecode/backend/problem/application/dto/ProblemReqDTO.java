package com.shallwecode.backend.problem.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ProblemReqDTO {
    private Long problemId;
    private String title;
    private String content;
    private int problemLevel;

}
