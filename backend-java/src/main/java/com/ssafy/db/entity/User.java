package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

/**
 * 유저 모델 정의.
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
//@AttributeOverride(name = "id", column = @Column(name = "user_idx"))
public class User {

    @Id
    @Column(name = "user_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "user_id", length=10, nullable = false)
    String userId;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    @Column(length = 10, nullable = false)
    String name;

//    @Column(length = 30, nullable = false)
//    String email;
    // 기본이미지 url 저장
//    @ColumnDefault("")
//    String profileImg;

//    @Column(nullable = false)
//    int age;

//    @Column(nullable = false)
//    boolean gender;

//    @Column(length = 10, nullable = false)
//    String nickname;
//
//    @Column(nullable = false)
//    int height;
//
//    @Column(nullable = false)
//    int weight;
//
//    @Column(nullable = false)
//    int footSize;
//
//    @Column(nullable = false)
//    String region;

    @Builder
    public void updateUser(String user_name, String user_password) {
        this.name = user_name;
        this.password = user_password;
    }


}
