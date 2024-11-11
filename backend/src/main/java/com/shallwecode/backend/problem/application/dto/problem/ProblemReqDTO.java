package com.shallwecode.backend.problem.application.dto.problem;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class ProblemReqDTO {

    private String title;
    private String content;
    private int problemLevel;
    private List<TestcaseDTO> testcases;

}
