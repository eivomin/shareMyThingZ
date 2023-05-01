package com.hanghae.sharemythingz.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false) // 제약조건 추가
    private Long id;

    @Column(unique = true, nullable = true)
    private String username;

    @Column(nullable = true)
    private Long kakaoId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Column
    private String image_url; // 대표 이미지

    public User(String username, String password, String email, UserRoleEnum role, String image_url) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.image_url = image_url;
    }

    public User(String username, String password, String email, UserRoleEnum role, String image_url, Long kakaoId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.image_url = image_url;
        this.kakaoId = kakaoId;
    }

    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }

}
