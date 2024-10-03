package com.farmstory.repository;

import com.farmstory.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    public List<Comment> findAllByArticleArtNo(int artNo);

    @Modifying
    @Transactional
    @Query("DELETE FROM Comment c WHERE c.article.artNo = :articleNo AND c.commentNo = :commentNo")
    void deleteByArticleNoAndCommentNo(@Param("articleNo") int articleNo, @Param("commentNo") int commentNo);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.article.artNo = :articleNo")
    int countByArticleNo(@Param("articleNo") int articleNo);



}
