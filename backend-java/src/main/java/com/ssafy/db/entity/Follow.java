package com.ssafy.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Follow extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    User fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    User toUser;
}
