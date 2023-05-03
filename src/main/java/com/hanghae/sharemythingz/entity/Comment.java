package com.hanghae.sharemythingz.entity;

import com.hanghae.sharemythingz.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@DynamicInsert
public class Comment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id", unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 댓글의 좋아요는 0부터 시작
    @ColumnDefault("0")
    @Column(nullable = false)
    private Long like_cnt;

    @ColumnDefault("0")
    @Column(nullable = false)
    private Long group_id;

    public Comment(CommentRequestDto requestDto, Post post, User user) {
        this.content = requestDto.getContent();
        this.post = post;
        this.user = user;
        this.like_cnt = 0L;
        this.group_id = 0L;
    }

    public void update(CommentRequestDto requestDto){
        this.content = requestDto.getContent();
    }

    public void addLike(){
        this.like_cnt += 1;
    }

    public void deleteLike(){
        this.like_cnt -= 1;
    }
}
