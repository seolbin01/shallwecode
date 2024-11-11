package com.shallwecode.backend.problem.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveTryReqDTO {

    private String coopList;
    private boolean isSolved;
    private String tryLanguage;
    private String codeContent;
}
