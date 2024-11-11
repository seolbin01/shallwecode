package com.shallwecode.backend.problem.application.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class ProblemListResDTO {
    private List<ProblemDTO> problemList; // 문제 목록
    private int currentPage;              // 현재 페이지
    private int totalPages;               // 전체 페이지 수
    private Long totalItems;              // 총 아이템 수
}
