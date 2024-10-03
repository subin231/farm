package com.farmstory.repository.article;

import com.farmstory.entity.Article;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer>, ArticleRepositoryCustom{
}
