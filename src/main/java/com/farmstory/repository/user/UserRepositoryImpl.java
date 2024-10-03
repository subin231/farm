package com.farmstory.repository.user;

import com.farmstory.dto.pageDTO.PageRequestDTO;
import com.farmstory.entity.QUser;
import com.farmstory.entity.User;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private final EntityManager entityManager;

    private QUser qUser = QUser.user;
    @Override
    public Page<Tuple> selectUserAllForList(PageRequestDTO pageRequestDTO, Pageable pageable) {
        List<Tuple> content = null;
        long total = 0;
        content = queryFactory.select(qUser, qUser)
                .from(qUser)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qUser.userUid.desc())
                .fetch();
        total = queryFactory
                .select(qUser.count())
                .from(qUser)
                .fetchOne();
        return new PageImpl<Tuple>(content, pageable, total);
    }

    @Override
    @Transactional
    public long updateUserPoint(int usePoint, int plusPoint , String userId) {

        System.out.println("point값은?" + (plusPoint-usePoint));
        System.out.println("uid값은?" + userId);

        long result = 0;

        result = queryFactory.update(qUser)
                .set(qUser.userTotalPoint, qUser.userTotalPoint.add(plusPoint-usePoint))
                .where(qUser.userUid.eq(userId))
                .execute();


        entityManager.flush();
        return result;

    }

}
