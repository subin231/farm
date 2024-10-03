package com.farmstory.repository.article;

import com.farmstory.dto.pageDTO.ArticlePageRequestDTO;
import com.farmstory.dto.pageDTO.PageRequestDTO;
import com.farmstory.entity.Article;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepositoryCustom  {

    public List<Article> selectArticles (String cate);
    public Article selectArticle (int no);
    public Page<Tuple> selectArticleAllForList(ArticlePageRequestDTO articlePageRequestDTO , Pageable pageable, String catetype);
    public Page<Tuple> selectArticleForSearch(ArticlePageRequestDTO articlePageRequestDTO, Pageable pageable);
}