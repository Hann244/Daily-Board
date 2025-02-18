package com.team5.first_project.post.repository;

import com.team5.first_project.board.entity.Board;
import com.team5.first_project.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByBoard(Board board, Pageable pageable);

    Page<Post> findByBoardAndTitleContainingOrBoardAndContentContaining(Board board1, String keyword1, Board board2, String keyword2, Pageable pageable);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Post p set p.view = p.view + 1 where p.id = :id")
    int updateView(@Param("id") Long id);
}
