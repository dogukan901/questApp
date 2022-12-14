package com.questApp.questApp.controllers;


import com.questApp.questApp.entities.Post;
import com.questApp.questApp.entities.User;
import com.questApp.questApp.requests.PostCreateRequest;
import com.questApp.questApp.requests.PostUpdateRequest;
import com.questApp.questApp.sevices.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> getAllPosts(@RequestParam Optional<Long> userId) {
        return postService.getAllPosts(userId);
    }

    @PostMapping
    public Post createOnePost(@RequestBody PostCreateRequest newPostRequest) {
        return postService.createOnePost(newPostRequest);
    }

    @GetMapping("/{postId}")
    public Post getOnePost(@PathVariable Long postId) {
        return postService.getOnePostById(postId);
    }

    @PutMapping("/{postId}")
    public Post updateOnePost(@PathVariable Long postId, @RequestBody PostUpdateRequest postUpdateRequest) {
        return postService.updateOnePostById(postId, postUpdateRequest);
    }

    @DeleteMapping("/{postId}")
    public Optional<Post> deleteOnePost(@PathVariable Long postId) {
        return postService.deleteOnePostById(postId);
    }
}
