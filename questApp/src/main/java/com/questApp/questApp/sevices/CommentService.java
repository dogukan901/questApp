package com.questApp.questApp.sevices;


import com.questApp.questApp.entities.Comment;
import com.questApp.questApp.entities.Post;
import com.questApp.questApp.entities.User;
import com.questApp.questApp.repos.CommentRepository;
import com.questApp.questApp.requests.CommentCreateRequest;
import com.questApp.questApp.requests.CommentUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private PostService postService;
    private UserService userService;


    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.userService = userService;
    }

    public List<Comment> getAllComments(Optional<Long> userId, Optional<Long> postId) {
        if (postId.isPresent() && userId.isPresent())
            return commentRepository.findByUserIdAndPostId(userId.get(), postId.get());
        else if (userId.isPresent())
            return commentRepository.findByUserId(userId.get());
        else if (postId.isPresent())
            return commentRepository.findByPostId(postId.get());
        else
            return commentRepository.findAll();
    }

    public Comment getOneCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public Comment createOneComment(CommentCreateRequest newCommentRequest) {
        User user = userService.getOneUserById(newCommentRequest.getUserId());
        Post post = postService.getOnePostById(newCommentRequest.getPostId());
        if (user != null && post != null) {
            Comment commentToSave = new Comment();
            commentToSave.setId(newCommentRequest.getId());
            commentToSave.setPost(post);
            commentToSave.setUser(user);
            commentToSave.setText(newCommentRequest.getText());
            return commentRepository.save(commentToSave);
        }else
            return null;
    }

    public Comment updateOneCommentById(Long commentId, CommentUpdateRequest commentUpdateRequest) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            Comment foundCommentToUpdate = comment.get();
            foundCommentToUpdate.setText(commentUpdateRequest.getText());
            commentRepository.save(foundCommentToUpdate);
            return foundCommentToUpdate;
        } else
            return null;
    }

    public Optional<Comment> deleteOneCommentById(Long commentId) {
        try {
            Optional<Comment> comment = commentRepository.findById(commentId);
            commentRepository.deleteById(commentId);
            return comment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
