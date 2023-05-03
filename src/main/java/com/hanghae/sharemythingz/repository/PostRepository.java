package com.hanghae.sharemythingz.repository;

import com.hanghae.sharemythingz.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByIdAndUserId(Long id, Long userId);
    List<Post> findPostsByBoardIdOrderByLastModifiedDateDesc(Long boardId);
}
