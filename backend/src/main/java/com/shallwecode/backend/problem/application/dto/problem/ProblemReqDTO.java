package com.shallwecode.backend.problem.application.dto.problem;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ProblemReqDTO {

    private String title;
    private String content;
    private int problemLevel;
    private List<TestcaseDTO> testcases;

}
