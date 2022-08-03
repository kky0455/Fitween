package com.ssafy.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QChatRoom is a Querydsl query type for ChatRoom
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QChatRoom extends EntityPathBase<ChatRoom> {

    private static final long serialVersionUID = 1315677523L;

    public static final QChatRoom chatRoom = new QChatRoom("chatRoom");

    public final StringPath lastChat = createString("lastChat");

    public final DateTimePath<java.time.LocalDateTime> lastChatTime = createDateTime("lastChatTime", java.time.LocalDateTime.class);

    public final StringPath roomId = createString("roomId");

    public final StringPath user1Id = createString("user1Id");

    public final StringPath user2Id = createString("user2Id");

    public QChatRoom(String variable) {
        super(ChatRoom.class, forVariable(variable));
    }

    public QChatRoom(Path<? extends ChatRoom> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChatRoom(PathMetadata metadata) {
        super(ChatRoom.class, metadata);
    }

}

