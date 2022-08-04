package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.ssafy.api.request.UserUpdateDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 유저 모델 정의.
 */
@Entity
@Getter@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@AttributeOverride(name = "id", column = @Column(name = "user_idx"))
public class User {

    @Id
    @Column(name = "user_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "user_id", length=50, nullable = false)
    String userId;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    @Column(length = 10, nullable = false)
    String name;

    @Column(name = "프로필이미지")
    String profileImg;

    @Column(name = "이메일")
    String email;

    @Column(name = "나이")
    int age;

    @Column(name = "성별")
    int gender;

    @Column(name = "닉네임")
    String nickname;

    @Column(name = "키")
    double height;

    @Column(name = "몸무게")
    double weight;

    @Column(name = "발사이즈")
    int footSize;

    @Column(name = "사는 지역")
    String region;

    @Column(name = "member_enable")
    private boolean enable;

    @Column(name = "refresh_token")
    private String refreshToken;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public void changeRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    @Builder
    public void updateUser(UserUpdateDto userUpdateDto) {
        this.userId = userUpdateDto.getUserId();
        this.name = userUpdateDto.getName();
        this.profileImg = userUpdateDto.getProfileImg();
        this.email = userUpdateDto.getEmail();
        this.age = userUpdateDto.getAge();
        this.gender = userUpdateDto.getGender();
        this.nickname = userUpdateDto.getNickname();
        this.height = userUpdateDto.getHeight();
        this.weight = userUpdateDto.getWeight();
        this.footSize = userUpdateDto.getFootSize();
        this.region = userUpdateDto.getRegion();
    }


}
