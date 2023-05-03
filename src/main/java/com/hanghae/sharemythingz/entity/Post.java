package com.hanghae.sharemythingz.entity;

import com.hanghae.sharemythingz.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@DynamicInsert
public class Post extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long boardId;

    @Column(nullable = false)
    private String img_url;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // 좋아요는 0부터 시작
    @ColumnDefault("0")
    @Column(nullable = false)
    private Long like_cnt;

    // 조회수는 0부터 시작
    @ColumnDefault("0")
    @Column(nullable = false)
    private Long view_cnt;


    public Post(PostRequestDto requestDto, User user){
        this.boardId = requestDto.getBoardId();
        this.img_url = requestDto.getImage_url();
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.user = user;
        this.like_cnt = 0L;
        this.view_cnt = 0L;
    }


    public void update(PostRequestDto requestDto) {
    }

    public void addLike() {
    }

    public void deleteLike() {
    }
}
