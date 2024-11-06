package com.shallwecode.backend.problem.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FindProblemResDTO {

    private Long problemId;
    private String title;
    private int level;
}
