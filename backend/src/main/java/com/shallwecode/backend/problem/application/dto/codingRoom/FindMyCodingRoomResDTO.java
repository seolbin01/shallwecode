package com.shallwecode.backend.problem.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FindMyCodingRoomResDTO {

    private Long codingRoomId;
    private Long problemId;
    private String problemTitle;
    private boolean isOpen;
    private int coopCount;
}