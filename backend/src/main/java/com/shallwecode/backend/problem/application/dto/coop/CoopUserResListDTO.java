package com.shallwecode.backend.problem.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CoopUserResListDTO {
    private String message;
    List<com.shallwecode.backend.problem.application.dto.CoopUserResDTO> coopList;
}
