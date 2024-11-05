package com.shallwecode.backend.problem.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coop")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coopId;

    private Long codingRoomId;
    private Long userId;
    private boolean isHost;

}
