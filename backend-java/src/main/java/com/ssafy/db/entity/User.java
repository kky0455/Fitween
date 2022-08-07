package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    Long userIdx;

    @Column(name = "user_id", length=10, nullable = false)
    String userId;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    @Column(length = 10, nullable = false)
    String name;

    @OneToMany(mappedBy = "user",  cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnoreProperties({"user"})
    List<Article> articles = new ArrayList<>();

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    List<Likes> likes = new ArrayList<>();


    @Builder
    public User(String userId, String password, String name) {
        this.userId = userId;
        this.password = password;
        this.name = name;
    }


    public void updateUser(String user_name, String user_password) {
        this.name = user_name;
        this.password = user_password;
    }

//    public void addLike(Likes likes) {
//        this.likes.add(likes);
//        likes.setUser(this);
//    }
//
//    public void removeLike(Likes likes) {
//        this.likes.remove(likes);
//        likes.setUser(null);
//    }
}
