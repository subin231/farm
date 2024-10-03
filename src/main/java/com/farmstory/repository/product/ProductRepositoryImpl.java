package com.farmstory.repository.product;

import com.farmstory.dto.pageDTO.PageRequestDTO;
import com.farmstory.entity.Product;
import com.farmstory.entity.QProduct;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    private QProduct qProduct = QProduct.product;


    @Override
    public List<Product> selectProducts() {
        return queryFactory
                .select(qProduct)
                .from(qProduct)
                .fetch();
    }

    @Override
    public Product selectProduct(int prodNo) {
        return queryFactory.select(qProduct).from(qProduct).where(qProduct.prodNo.eq(prodNo)).fetchOne();
    }

    @Override
    public Page<Tuple> selectProductAllForList(PageRequestDTO pageRequestDTO, Pageable pageable, int catetype) {

        List<Tuple> content = null;
        long total = 0;

        if(catetype == 0) {
            content = queryFactory.select(qProduct, qProduct)
                    .from(qProduct)
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(qProduct.prodNo.desc())
                    .fetch();
            total = queryFactory
                    .select(qProduct.count())
                    .from(qProduct)
                    .fetchOne();
        }else if(catetype >= 1) {
            content = queryFactory.select(qProduct, qProduct)
                    .from(qProduct)
                    .where(qProduct.prodCateType.eq(catetype))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(qProduct.prodNo.desc())
                    .fetch();
            total = queryFactory
                    .select(qProduct.count())
                    .from(qProduct)
                    .where(qProduct.prodCateType.eq(catetype))
                    .fetchOne();
        }
        // 페이징 처리를 위해 page 객체 리턴
        return new PageImpl<Tuple>(content, pageable, total);
    }

}
