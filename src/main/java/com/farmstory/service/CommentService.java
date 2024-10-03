package com.farmstory.service;

import com.farmstory.dto.CommentDTO;
import com.farmstory.entity.Article;
import com.farmstory.entity.Comment;
import com.farmstory.entity.User;
import com.farmstory.repository.CommentRepository;
import com.farmstory.repository.user.UserRepository;
import com.farmstory.repository.article.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    public CommentDTO insertComment(CommentDTO commentDTO) {
        // 댓글 엔티티 변환
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        Article article = articleRepository.findById(commentDTO.getArtNo()).orElseThrow(() -> new RuntimeException());

        User user = User.builder()
                .userUid(commentDTO.getUserUid())
                .build();

        // 댓글에 유저 등록
        comment.setUser(user);
        comment.registerArticle(article);
        log.info(comment);
        // 댓글 생성
        Comment saveComments = commentRepository.save(comment);


        // dto 변환 후 반환
        CommentDTO dto = modelMapper.map(saveComments, CommentDTO.class);
        dto.setNick(user.getUserNick());

        log.info("dto : " +dto);

        return dto;
    }

    public CommentDTO selectComment(int no) {
        return null;

    }


    public void updateComment(CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(commentDTO.getCommentNo()).orElseThrow(() -> new RuntimeException("해당 댓글을 찾을 수 없습니다."));
        comment.setContent(commentDTO.getContent());
    }

    public int deleteComment(int articleNo, int commentNo) {
        commentRepository.deleteByArticleNoAndCommentNo(articleNo, commentNo);
        return articleNo;
    }

    public List<Comment> selectCommentByArtNo(int artNo){

      return commentRepository.findAllByArticleArtNo(artNo);
    }
}