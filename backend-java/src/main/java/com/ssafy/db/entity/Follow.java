package com.ssafy.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter @Setter
@NoArgsConstructor
public class Follow{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_idx")
    private Long followIdx;

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    User from;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    User to;

    @Builder
    public Follow(User from, User to){
        this.from = from;
        this.to = to;
    }
}
