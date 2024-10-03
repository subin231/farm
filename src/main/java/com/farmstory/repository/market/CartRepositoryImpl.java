package com.farmstory.repository.market;

import com.farmstory.entity.Cart;
import com.farmstory.entity.QCart;
import com.farmstory.entity.QProduct;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CartRepositoryImpl implements CartRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private QCart qCart = QCart.cart;
    private QProduct qProduct = QProduct.product;

}
