package com.farmstory.repository.article;


import com.farmstory.dto.pageDTO.ArticlePageRequestDTO;
import com.farmstory.dto.pageDTO.PageRequestDTO;
import com.farmstory.entity.Article;
import com.farmstory.entity.QArticle;
import com.farmstory.entity.QComment;
import com.farmstory.entity.QUser;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


@RequiredArgsConstructor
@Repository
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private QArticle qArticle =  QArticle.article;
    private QComment qComment = QComment.comment;
    private QUser qUser = QUser.user;

    @Override
    public List<Article> selectArticles(String cate) {
        return queryFactory.select(qArticle)
                            .from(qArticle)
                            .where(qArticle.artCate.eq(cate))  
                            .fetch();
    }

    @Override
    public Article selectArticle(int no) {
        return queryFactory.select(qArticle)
                .from(qArticle)
                .where(qArticle.artNo.eq(no))
                .fetchOne();
    }

    @Override
    public Page<Tuple> selectArticleAllForList(ArticlePageRequestDTO articlePageRequestDTO, Pageable pageable, String catetype) {
        List<Tuple> content = null;
        long total = 0;


            content = queryFactory.select(qArticle, qArticle)
                    .from(qArticle)
                    .where(qArticle.artCate.eq(catetype))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(qArticle.artNo.desc())
                    .fetch();
            total = queryFactory
                    .select(qArticle.count())
                    .from(qArticle)
                    .where(qArticle.artCate.eq(catetype))
                    .fetchOne();


        // 페이징 처리를 위해 page 객체 리턴
        return new PageImpl<Tuple>(content, pageable, total);
    }


    @Override
    public Page<Tuple> selectArticleForSearch(ArticlePageRequestDTO articlePageRequestDTO, Pageable pageable) {

        String type = articlePageRequestDTO.getType();
        String keyword = articlePageRequestDTO.getKeyword();

        // 검색 선택 조건에 따라 where 조건 표현식 생성
        BooleanExpression expression = null;

        if(type.equals("title")){
            expression = qArticle.artTitle.contains(keyword);

        }else if(type.equals("content")){
            expression = qArticle.artContent.contains(keyword);

        }else if(type.equals("title_content")){

            BooleanExpression titleExpression = qArticle.artTitle.contains(keyword);
            BooleanExpression contentExpression = qArticle.artContent.contains(keyword);

            expression = titleExpression.or(contentExpression);

        }else if(type.equals("writer")){
            expression = qUser.userUid.contains(keyword);
        }

        List<Tuple> content  = queryFactory
                .select(qArticle, qUser.userNick)
                .from(qArticle)
                .join(qUser)
                .on(qArticle.artWriter.eq(qUser.userUid))
                .where(expression)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qArticle.artNo.desc())
                .fetch();


        long total = queryFactory
                .select(qArticle.count())
                .from(qArticle)
                .where(expression)
                .join(qUser)
                .on(qArticle.artWriter.eq(qUser.userUid))
                .fetchOne();

        // 페이징 처리를 위해 page 객체 리턴
        return new PageImpl<Tuple>(content, pageable, total);
    }


}