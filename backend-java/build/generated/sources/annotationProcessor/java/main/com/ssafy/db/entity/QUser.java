package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 846542477L;

    public static final QUser user = new QUser("user");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final ListPath<Article, QArticle> articles = this.<Article, QArticle>createList("articles", Article.class, QArticle.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createTime = _super.createTime;

    public final StringPath dateOfBirth = createString("dateOfBirth");

    public final StringPath email = createString("email");

    public final BooleanPath enable = createBoolean("enable");

    public final NumberPath<Integer> footSize = createNumber("footSize", Integer.class);

    public final StringPath gender = createString("gender");

    public final NumberPath<Double> height = createNumber("height", Double.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastUpdateTime = _super.lastUpdateTime;

    public final ListPath<Likes, QLikes> likes = this.<Likes, QLikes>createList("likes", Likes.class, QLikes.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath profileImg = createString("profileImg");

    public final StringPath refreshToken = createString("refreshToken");

    public final StringPath region = createString("region");

    public final ListPath<String, StringPath> roles = this.<String, StringPath>createList("roles", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath userId = createString("userId");

    public final NumberPath<Long> userIdx = createNumber("userIdx", Long.class);

    public final NumberPath<Double> weight = createNumber("weight", Double.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

