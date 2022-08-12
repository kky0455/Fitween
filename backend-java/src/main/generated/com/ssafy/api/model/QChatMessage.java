package com.ssafy.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QChatMessage is a Querydsl query type for ChatMessage
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QChatMessage extends EntityPathBase<ChatMessage> {

    private static final long serialVersionUID = -947328881L;

    public static final QChatMessage chatMessage = new QChatMessage("chatMessage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> isRead = createNumber("isRead", Integer.class);

    public final StringPath message = createString("message");

    public final StringPath receiverId = createString("receiverId");

    public final StringPath roomId = createString("roomId");

    public final DateTimePath<java.time.LocalDateTime> senddatetime = createDateTime("senddatetime", java.time.LocalDateTime.class);

    public final StringPath senderId = createString("senderId");

    public QChatMessage(String variable) {
        super(ChatMessage.class, forVariable(variable));
    }

    public QChatMessage(Path<? extends ChatMessage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChatMessage(PathMetadata metadata) {
        super(ChatMessage.class, metadata);
    }

}

