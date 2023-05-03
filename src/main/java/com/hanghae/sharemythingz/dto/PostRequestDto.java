package com.hanghae.sharemythingz.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private Long boardId;
    private String title;
    private String image_url;
    private String content;
}
