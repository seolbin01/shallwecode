package com.shallwecode.backend.user.domain.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name="user_info")
@Getter
@ToString
@AllArgsConstructor
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Enumerated(EnumType.STRING)
    private SocialType provider;
    @Column(unique = true, nullable = false)
    private String email;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private AuthType auth;
    private LocalDateTime createdAt;
    private String socialId;
    private String refreshToken;

//    @OneToMany(mappedBy = "fromUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Friend> friendsFrom;
//
//    @OneToMany(mappedBy = "toUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Friend> friendsTo;
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Noti> notiList;

    public UserInfo()
    {}

    public UserInfo createUser(SocialType provider, String socialId, String email, AuthType authType) {
        this.provider = provider;
        this.socialId = socialId;
        this.email = email;
        this.auth = authType;
        return this;
    }

    public void updateUser(String nickname){
        this.nickname = nickname;
    }

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateAuth() {
        this.auth = AuthType.USER;
    }
}
