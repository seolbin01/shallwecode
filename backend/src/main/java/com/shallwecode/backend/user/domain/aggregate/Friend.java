package com.shallwecode.backend.user.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "friend")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendId;

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private UserInfo fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private UserInfo toUser;

    @Enumerated(EnumType.STRING)
    private FriendStatus friendStatus;

    @PrePersist
    public void prePersist() {
        friendStatus = FriendStatus.PENDING;
    }
}

