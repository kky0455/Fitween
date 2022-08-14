package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.ssafy.api.request.RegionUpdateDto;
import com.ssafy.api.request.UserUpdateDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 유저 모델 정의.
 */
@Entity @Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity{

    @Id
    @Column(name = "user_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userIdx;

    @Column(name = "user_id", length=50, nullable = false)
    String userId;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    @Column(length = 10)
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
    double height;

    @Column
    double weight;

    @Column
    int footSize;

    @Column
    String region;

    @Column
    private boolean enable;

    @Column(name = "refresh_token")
    private String refreshToken;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"user", "likes", "articles", "article"})
    List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"user", "likes", "articles", "article"})
    private List<Likes> likes = new ArrayList<>();

    public void changeRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    @Builder
    public void updateUser(UserUpdateDto userUpdateDto) {
        this.profileImg = userUpdateDto.getProfileImg();
        this.nickname = userUpdateDto.getNickname();
        this.height = userUpdateDto.getHeight();
        this.weight = userUpdateDto.getWeight();
        this.footSize = userUpdateDto.getFootSize();
    }

    @Builder
    public void updateRegion(RegionUpdateDto regionUpdateDto) {
        this.region = regionUpdateDto.getRegion();
    }

}
