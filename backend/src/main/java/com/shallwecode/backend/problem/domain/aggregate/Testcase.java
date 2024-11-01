package com.shallwecode.backend.problem.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "testcase")
@Getter
public class Testcase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testcaseId;
    private Long problemId;
    private String input;
    private String output;
}
