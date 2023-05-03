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

    @Column(nullable = false)
    private Long group_id;

    public Comment(CommentRequestDto requestDto, Post post, User user) {
        super();
    }

    public void update(CommentRequestDto requestDto) {
    }

    public void addLike() {
    }

    public void deleteLike() {
    }
}
