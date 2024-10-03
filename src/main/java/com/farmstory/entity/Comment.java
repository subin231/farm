package com.farmstory.entity;

import com.farmstory.dto.CommentDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"article", "user"}) // 양방향 관계 필드를 제외
@Builder
@Entity                 // 엔티티 객체 정의
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentNo;
    private String commentRegIp;
    private String content;

    @CreationTimestamp
    private LocalDateTime commentRegDate;

    @ManyToOne
    @JoinColumn(name = "writer")
    private User user;

    @ManyToOne
    @JoinColumn(name = "artNo")
    private Article article;

    public CommentDTO toDTO() {
        return CommentDTO.builder()
                .commentNo(commentNo)
                .artNo(article.getArtNo())
                .commentRegIp(commentRegIp)
                .commentRegDate(commentRegDate)
                .build();
    }

    public void registerUser(User user) {
        this.user = user;
    };
    public void registerArticle(Article article) {this.article = article;};
}
