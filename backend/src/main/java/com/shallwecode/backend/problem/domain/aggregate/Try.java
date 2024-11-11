package com.shallwecode.backend.problem.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "try")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Try {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tryId;
    private Long userId;
    private Long problemId;
    private String coopList;
    private boolean isSolved;
    @Enumerated(EnumType.STRING)
    private TryLanguage tryLanguage;
    private String codeContent;
    private LocalDateTime createdAt = LocalDateTime.now();

    public void updateUser(Long userId) {
        this.userId = userId;
    }

    public void updateProblem(Long problemId) {
        this.problemId = problemId;
    }

    public void updateLanguage(TryLanguage tryLanguage) {
        this.tryLanguage = tryLanguage;
    }
}
