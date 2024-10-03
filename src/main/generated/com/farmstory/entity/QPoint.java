package com.farmstory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPoint is a Querydsl query type for Point
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPoint extends EntityPathBase<Point> {

    private static final long serialVersionUID = -186120767L;

    public static final QPoint point = new QPoint("point");

    public final DateTimePath<java.time.LocalDateTime> pointDate = createDateTime("pointDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> pointNo = createNumber("pointNo", Integer.class);

    public final NumberPath<Integer> pointValue = createNumber("pointValue", Integer.class);

    public final StringPath userId = createString("userId");

    public QPoint(String variable) {
        super(Point.class, forVariable(variable));
    }

    public QPoint(Path<? extends Point> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPoint(PathMetadata metadata) {
        super(Point.class, metadata);
    }

}

