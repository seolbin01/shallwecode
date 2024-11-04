package com.shallwecode.backend.problem.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ProblemResListDTO {
    private Long problemId;
    private String title;
    private String content;
    private int problemLevel;
}
