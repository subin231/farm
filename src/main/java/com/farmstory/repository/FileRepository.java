package com.farmstory.repository;

import com.farmstory.entity.Article;
import com.farmstory.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {
    public List<FileEntity> findAllByArticle(Article article);
    List<FileEntity> findByArticle(Article article);
    List<FileEntity> findByArticleArtNo(int artNo);
}
