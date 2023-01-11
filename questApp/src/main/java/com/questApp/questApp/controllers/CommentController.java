package com.questApp.questApp.controllers;


import com.questApp.questApp.entities.Comment;
import com.questApp.questApp.requests.CommentCreateRequest;
import com.questApp.questApp.requests.CommentUpdateRequest;
import com.questApp.questApp.sevices.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private CommentService commentService;


    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    public List<Comment> getAllComments(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId) {
        return commentService.getAllComments(userId, postId);
    }


    @GetMapping("/{commentId}")
    public Comment getOneComment(@PathVariable Long commentId) {
        return commentService.getOneCommentById(commentId);
    }

    @PostMapping
    public Comment createOneComment(@RequestBody CommentCreateRequest newCommentRequest) {
        return commentService.createOneComment(newCommentRequest);
    }

    @PutMapping("/{commentId}")
    public Comment updateOneComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequest commentUpdateRequest) {
        return commentService.updateOneCommentById(commentId, commentUpdateRequest);
    }

    @DeleteMapping("/{commentId}")
    public Optional<Comment> deleteOneComment(@PathVariable Long commentId) {
        return commentService.deleteOneCommentById(commentId);
    }


}
