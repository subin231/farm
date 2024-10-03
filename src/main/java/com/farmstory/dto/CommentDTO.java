package com.farmstory.dto;

import com.farmstory.entity.Article;
import com.farmstory.entity.Comment;
import com.farmstory.entity.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO {
    private int commentNo;
    private int artNo;
    private String userUid;
    private String commentRegIp;
    private String content;
    private String nick;

    @CreationTimestamp
    private LocalDateTime commentRegDate;


    private ArticleDTO article;


    public Comment toEntity(Article article, User user) {
        return Comment.builder()
                .commentNo(commentNo)
                .commentRegIp(commentRegIp)
                .commentRegDate(commentRegDate)
                .content(content)
                .article(article)
                .user(user)
                .build();
    }
}
