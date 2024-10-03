package com.farmstory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArticle is a Querydsl query type for Article
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArticle extends EntityPathBase<Article> {

    private static final long serialVersionUID = 1194800359L;

    public static final QArticle article = new QArticle("article");

    public final StringPath artCate = createString("artCate");

    public final NumberPath<Integer> artComment = createNumber("artComment", Integer.class);

    public final StringPath artContent = createString("artContent");

    public final NumberPath<Integer> artFile = createNumber("artFile", Integer.class);

    public final NumberPath<Integer> artHit = createNumber("artHit", Integer.class);

    public final StringPath artNick = createString("artNick");

    public final NumberPath<Integer> artNo = createNumber("artNo", Integer.class);

    public final DatePath<java.time.LocalDate> artRdate = createDate("artRdate", java.time.LocalDate.class);

    public final StringPath artRegip = createString("artRegip");

    public final StringPath artTitle = createString("artTitle");

    public final StringPath artWriter = createString("artWriter");

    public final ListPath<Comment, QComment> comments = this.<Comment, QComment>createList("comments", Comment.class, QComment.class, PathInits.DIRECT2);

    public final ListPath<FileEntity, QFileEntity> fileList = this.<FileEntity, QFileEntity>createList("fileList", FileEntity.class, QFileEntity.class, PathInits.DIRECT2);

    public QArticle(String variable) {
        super(Article.class, forVariable(variable));
    }

    public QArticle(Path<? extends Article> path) {
        super(path.getType(), path.getMetadata());
    }

    public QArticle(PathMetadata metadata) {
        super(Article.class, metadata);
    }

}

