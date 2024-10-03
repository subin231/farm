package com.farmstory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1668419206L;

    public static final QUser user = new QUser("user");

    public final StringPath userAddr1 = createString("userAddr1");

    public final StringPath userAddr2 = createString("userAddr2");

    public final StringPath userEmail = createString("userEmail");

    public final StringPath userHp = createString("userHp");

    public final DateTimePath<java.time.LocalDateTime> userLeaveDate = createDateTime("userLeaveDate", java.time.LocalDateTime.class);

    public final StringPath userName = createString("userName");

    public final StringPath userNick = createString("userNick");

    public final StringPath userPass = createString("userPass");

    public final DateTimePath<java.time.LocalDateTime> userRegDate = createDateTime("userRegDate", java.time.LocalDateTime.class);

    public final StringPath userRegip = createString("userRegip");

    public final StringPath userRole = createString("userRole");

    public final NumberPath<Integer> userTotalPoint = createNumber("userTotalPoint", Integer.class);

    public final StringPath userUid = createString("userUid");

    public final StringPath userZip = createString("userZip");

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

