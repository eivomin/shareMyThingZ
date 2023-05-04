package com.hanghae.sharemythingz.repository;

import com.hanghae.sharemythingz.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByIdAndUserId(Long id, Long userId);
    Page<Post> findByBoardIdOrderByLastModifiedDateDesc(Long boardId, Pageable pageable);
    Page<Post> findByTitleContaining(String searchKeyword, Pageable pageable);
}
