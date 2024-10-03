package com.farmstory.repository.order;

import com.farmstory.dto.pageDTO.PageRequestDTO;
import com.farmstory.entity.QOrder;
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
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private QOrder qOrder = QOrder.order;

    @Override
    public Page<Tuple> selectOrderAllForList(PageRequestDTO pageRequestDTO, Pageable pageable) {
        List<Tuple> content = null;
        long total = 0;
        content = queryFactory.select(qOrder, qOrder)
                .from(qOrder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qOrder.orderNo.desc())
                .fetch();
        total = queryFactory
                .select(qOrder.count())
                .from(qOrder)
                .fetchOne();
        return new PageImpl<Tuple>(content, pageable, total);
    }
}
