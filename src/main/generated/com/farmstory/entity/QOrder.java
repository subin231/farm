package com.farmstory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = -186960001L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrder order = new QOrder("order1");

    public final StringPath orderAddr = createString("orderAddr");

    public final DateTimePath<java.time.LocalDateTime> orderDate = createDateTime("orderDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> orderDeliveryFee = createNumber("orderDeliveryFee", Integer.class);

    public final NumberPath<Integer> orderDiscount = createNumber("orderDiscount", Integer.class);

    public final NumberPath<Integer> orderItemDiscount = createNumber("orderItemDiscount", Integer.class);

    public final StringPath orderMemo = createString("orderMemo");

    public final NumberPath<Integer> orderNo = createNumber("orderNo", Integer.class);

    public final NumberPath<Integer> orderPay = createNumber("orderPay", Integer.class);

    public final NumberPath<Integer> orderPlusPoint = createNumber("orderPlusPoint", Integer.class);

    public final NumberPath<Integer> orderPrice = createNumber("orderPrice", Integer.class);

    public final StringPath orderReceiveHp = createString("orderReceiveHp");

    public final StringPath orderReceiveName = createString("orderReceiveName");

    public final StringPath orderSendHp = createString("orderSendHp");

    public final StringPath orderSendName = createString("orderSendName");

    public final NumberPath<Integer> ordersGroup = createNumber("ordersGroup", Integer.class);

    public final NumberPath<Integer> orderStock = createNumber("orderStock", Integer.class);

    public final NumberPath<Integer> orderTotalPrice = createNumber("orderTotalPrice", Integer.class);

    public final NumberPath<Integer> orderUsePoint = createNumber("orderUsePoint", Integer.class);

    public final QProduct product;

    public final QUser user;

    public QOrder(String variable) {
        this(Order.class, forVariable(variable), INITS);
    }

    public QOrder(Path<? extends Order> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrder(PathMetadata metadata, PathInits inits) {
        this(Order.class, metadata, inits);
    }

    public QOrder(Class<? extends Order> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

