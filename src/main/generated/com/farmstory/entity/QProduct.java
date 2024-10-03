package com.farmstory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 1617704160L;

    public static final QProduct product = new QProduct("product");

    public final NumberPath<Integer> prodCateType = createNumber("prodCateType", Integer.class);

    public final NumberPath<Integer> prodDelivery = createNumber("prodDelivery", Integer.class);

    public final NumberPath<Integer> prodDiscount = createNumber("prodDiscount", Integer.class);

    public final StringPath prodEtc = createString("prodEtc");

    public final StringPath prodImage1 = createString("prodImage1");

    public final StringPath prodImage2 = createString("prodImage2");

    public final StringPath prodImage3 = createString("prodImage3");

    public final StringPath prodName = createString("prodName");

    public final NumberPath<Integer> prodNo = createNumber("prodNo", Integer.class);

    public final NumberPath<Integer> prodPoint = createNumber("prodPoint", Integer.class);

    public final NumberPath<Integer> prodPrice = createNumber("prodPrice", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> prodRegDate = createDateTime("prodRegDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> prodStock = createNumber("prodStock", Integer.class);

    public QProduct(String variable) {
        super(Product.class, forVariable(variable));
    }

    public QProduct(Path<? extends Product> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProduct(PathMetadata metadata) {
        super(Product.class, metadata);
    }

}

