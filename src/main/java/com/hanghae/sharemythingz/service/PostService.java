package com.hanghae.sharemythingz.service;

import com.hanghae.sharemythingz.dto.PostRequestDto;
import com.hanghae.sharemythingz.dto.PostResponseDto;
import com.hanghae.sharemythingz.entity.Post;
import com.hanghae.sharemythingz.entity.PostLike;
import com.hanghae.sharemythingz.entity.User;
import com.hanghae.sharemythingz.entity.UserRoleEnum;
import com.hanghae.sharemythingz.jwt.JwtUtil;
import com.hanghae.sharemythingz.repository.PostLikeRepository;
import com.hanghae.sharemythingz.repository.PostRepository;
import com.hanghae.sharemythingz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;
    private final JwtUtil jwtUtil;

    /* 게시글 작성 */
    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        Post post = postRepository.saveAndFlush(new Post(requestDto, user));
        return new PostResponseDto(post);
    }

    /* 게시글 전체 조회 (페이징 기능 추가) */
    public Page<PostResponseDto> findAll(Long board_id, Pageable pageable) {
        return postRepository.findByBoardIdOrderByLastModifiedDateDesc(board_id, pageable)
                .map(PostResponseDto::new);
    }

    /* 게시글 검색 조회 */
    public Page<PostResponseDto> postSearchList(String searchKeyword, Pageable pageable){
        return postRepository.findByTitleContaining(searchKeyword, pageable)
                .map(PostResponseDto::new);
    }

    /* 게시글 수정
     * ADMIN 회원은 모든 게시글 수정 가능
     *  */
    @Transactional
    public PostResponseDto update(Long id, PostRequestDto requestDto, User user) {
        Post post = new Post();
        // 관리자 검증 여부
        if(user.getRole().name().equals("ADMIN")){
            // ROLE == ADMIN
            post = postRepository.findById(id).orElseThrow(
                    IllegalStateException::new
            );
        }else{
            // ROLE == USER
            post = postRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    IllegalStateException::new
            );
        }
        post.update(requestDto);
        return new PostResponseDto(post);
    }

    /* 게시글 삭제
     * ADMIN 회원은 모든 게시글 삭제 가능
     *  */
    @Transactional
    public void deletePost(Long id, User user) {
        Post post = new Post();

        // 관리자 검증 여부
        if(user.getRole().equals(UserRoleEnum.ADMIN)){
            // ROLE == ADMIN
            post = postRepository.findById(id).orElseThrow(
                    IllegalStateException::new
            );
        }else{
            // ROLE == USER
            post = postRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    IllegalStateException::new
            );
        }
        postRepository.deleteById(id);
    }

    /* 게시글 상세 조회 */
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                IllegalStateException::new
        );
        return new PostResponseDto(post);
    }

    /* 게시글 좋아요 */
    @Transactional
    public boolean saveLikes(Long id, User user) {
        // 1. 해당 게시글 존재 여부
        Post post = postRepository.findById(id).orElseThrow(
                IllegalStateException::new
        );

        //user 좋아요 이력 확인
        Optional<PostLike> findPostLike = postLikeRepository.findByPostIdAndUserId(id, user.getId());

        if(findPostLike.isEmpty()) {
            postLikeRepository.save(new PostLike(post, user));
            post.addLike();
            return true;
        }
        return false;
    }

    /* 게시글 싫어요 */
    @Transactional
    public boolean deleteLikes(Long id, User user) {
        // 1. 해당 게시글 존재 여부
        Post post = postRepository.findById(id).orElseThrow(
                IllegalStateException::new
        );
        //user 좋아요 이력 확인
        Optional<PostLike> findPostLike = postLikeRepository.findByPostIdAndUserId(id, user.getId());

        if(findPostLike.isPresent()) {
            postLikeRepository.deleteById(findPostLike.get().getId());
            post.deleteLike();
            return true;
        }
        return false;
    }

}
