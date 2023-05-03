package com.hanghae.sharemythingz.dto;

import com.hanghae.sharemythingz.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 생성해줌
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String username;
    private List<CommentResponseDto> comments;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Long like_cnt;
    private String img_url;
    private Long boardId;

    public PostResponseDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.username = post.getUser().getUsername();
        this.comments = post.getComments().stream().map(CommentResponseDto::new).collect(Collectors.toList());
        this.createdDate = post.getCreatedDate();
        this.lastModifiedDate = post.getLastModifiedDate();
        this.like_cnt = post.getLike_cnt();
        this.img_url = post.getImg_url();
        this.boardId = post.getBoardId();
    }
}
