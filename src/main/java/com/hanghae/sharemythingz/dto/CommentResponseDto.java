package com.hanghae.sharemythingz.dto;

import com.hanghae.sharemythingz.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String content;
    private String username;
    private Long postId;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Long like_cnt;
    private String createdBy;
    private String lastModifiedBy;

    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.username = comment.getUser().getUsername();
        this.postId = comment.getPost().getId();
        this.createdDate = comment.getCreatedDate();
        this.lastModifiedDate = comment.getLastModifiedDate();
        this.like_cnt = comment.getLike_cnt();
    }
}