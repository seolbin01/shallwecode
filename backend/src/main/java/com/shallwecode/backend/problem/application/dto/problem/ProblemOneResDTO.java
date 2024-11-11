package com.shallwecode.backend.problem.application.dto.problem;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
public class ProblemOneResDTO {
    private Long problemId;
    private String title;
    private String content;
    private int problemLevel;
    private List<TestcaseDTO> testcases;

    // QueryDSL용 생성자 추가
    public ProblemOneResDTO(Long problemId, String title, String content, int problemLevel) {
        this.problemId = problemId;
        this.title = title;
        this.content = content;
        this.problemLevel = problemLevel;
    }
//    private Long testcaseId;
//    private String input;
//    private String output;
}
