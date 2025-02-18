package com.team5.first_project.comment.controller;

import com.team5.first_project.board.service.BoardService;
import com.team5.first_project.comment.dto.CommentRequestDto;
import com.team5.first_project.comment.dto.CommentResponseDto;
import com.team5.first_project.comment.service.CommentService;
import com.team5.first_project.member.entity.Member;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final BoardService boardService;

    /**
     * 댓글 생성
     */
    @PostMapping("/comments")
    public String createComment(@RequestParam("postId") long id,
                                @Valid @ModelAttribute CommentRequestDto commentRequestDto,
                                HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        commentService.createComment(id, commentRequestDto, member);
        return "redirect:/posts/" + id;
    }

    /**
     * 댓글 수정
     */
    @PostMapping("/comments/{commentId}/edit")
    public String updateComment(@PathVariable("commentId") long id,
                                @Valid @ModelAttribute CommentRequestDto commentRequestDto,
                                HttpSession session) {
        if (commentService.commentAuthorVerification(id, session)) {
            CommentResponseDto commentResponseDto = commentService.updateComment(id, commentRequestDto);
            return "redirect:/posts/" + commentResponseDto.getPostId();
        } else {
            CommentResponseDto commentResponseDto = commentService.findComment(id);
            return "redirect:/posts/" + commentResponseDto.getPostId();
        }
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") long id,
                                              HttpSession session) {
        if (commentService.commentAuthorVerification(id, session) ||
                boardService.administratorVerification(session)) {
            commentService.deleteComment(id);
            return ResponseEntity.noContent().build(); // 204 No Content 응답을 반환
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }


}
