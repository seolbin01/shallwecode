package com.shallwecode.backend.problem.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "coding_room")
@Getter
public class CodingRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codingRoomId;

    private Long problemId;

    @Enumerated(value = EnumType.STRING)
    private TryLanguage tryLanguage;
    private String codeContent;
    private boolean isOpen;

}
