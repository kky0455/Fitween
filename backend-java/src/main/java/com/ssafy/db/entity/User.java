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

    @Column
    String profileImg;

    @Column
    String email;

    @Column
    int age;

    @Column
    int gender;

    @Column
    String nickname;

    @Column
    int height;

    @Column
    int weight;

    @Column
    int footSize;

    @Column
    String region;

    @Builder
    public void updateUser(String user_name, String user_password) {
        this.name = user_name;
        this.password = user_password;
    }


}
