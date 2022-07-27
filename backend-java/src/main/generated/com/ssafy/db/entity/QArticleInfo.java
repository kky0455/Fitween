package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QArticleInfo is a Querydsl query type for ArticleInfo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QArticleInfo extends EntityPathBase<ArticleInfo> {

    private static final long serialVersionUID = 1818145218L;

    public static final QArticleInfo articleInfo = new QArticleInfo("articleInfo");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath feedImg = createString("feedImg");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final BooleanPath lend = createBoolean("lend");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final StringPath title = createString("title");

    public QArticleInfo(String variable) {
        super(ArticleInfo.class, forVariable(variable));
    }

    public QArticleInfo(Path<? extends ArticleInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QArticleInfo(PathMetadata metadata) {
        super(ArticleInfo.class, metadata);
    }

}

