package com.shallwecode.backend.user.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name="user_info")
@Getter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String provider;
    private String providerId;
    private String email;
    private String nickname;
    private String auth;
    private Date createdAt;
}
