package com.hanghae.sharemythingz.controller;

import com.hanghae.sharemythingz.dto.PostRequestDto;
import com.hanghae.sharemythingz.dto.PostResponseDto;
import com.hanghae.sharemythingz.dto.StatusResponseDto;
import com.hanghae.sharemythingz.security.UserDetailsImpl;
import com.hanghae.sharemythingz.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /* 게시글 작성 api */
    @PostMapping("/api/posts")
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.createPost(requestDto, userDetails.getUser());
    }

    /* 게시글 전체 조회 페이징 api */
    @GetMapping("/api/board/{id}/posts")
    public List<PostResponseDto> find(@PathVariable Long id, @PageableDefault(size = 7) Pageable pageable){
        return postService.findAll(id, pageable).getContent();
    }

    /* 게시글 전체 조회 + 검색 페이징 api */
    @GetMapping("/api/board/{id}/posts/list")
    public ModelAndView postList(@PathVariable Long id, @PageableDefault(page=0, size=5) Pageable pageable,
                                 String searchKeyword){
        Page<PostResponseDto> list = null;

        if(searchKeyword == null){
            list = postService.findAll(id, pageable);
        }else{
            list = postService.postSearchList(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber()+1;
        int startPage = Math.max(nowPage-4, 1);
        int endPage = Math.min(nowPage+5, list.getTotalPages());

        ModelAndView view = new ModelAndView();
        view.setViewName("pagingtest");
        view.addObject("list", list);
        view.addObject("nowPage", nowPage);
        view.addObject("startPage", startPage);
        view.addObject("endPage", endPage);
        return view;
    }

    /* 게시글 상세 조회 이동 api */
    @GetMapping("/api/board/posts/{id}")
    public ModelAndView movePost(@PathVariable Long id){
        ModelAndView view = new ModelAndView();
        view.setViewName("detail2");
        view.addObject("p_id", id);
        return view;
    }

    /* 게시글 상세 조회 api */
    @GetMapping("/api/posts/{id}")
    public PostResponseDto getPost(@PathVariable Long id){
        return postService.getPost(id);
    }

    /* 게시글 수정 api */
    @PutMapping("/api/posts/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.update(id, requestDto, userDetails.getUser());
    }

    /* 게시글 삭제 api */
    @DeleteMapping("/api/posts/{id}")
    public ResponseEntity<StatusResponseDto> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        postService.deletePost(id, userDetails.getUser());

        StatusResponseDto res = new StatusResponseDto(
                200,
                HttpStatus.OK,
                "게시글 삭제 성공"
        );
        return new ResponseEntity<>(res, res.getHttpStatus());
    }

    /* 게시글 좋아요 api */
    @PostMapping("/api/posts/{id}/like")
    public void likePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        boolean likeCheck = postService.saveLikes(id, userDetails.getUser());

        if(!likeCheck) {
            boolean check2 = postService.deleteLikes(id, userDetails.getUser());
        }
    }
}
