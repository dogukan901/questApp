package com.questApp.questApp.sevices;

import com.questApp.questApp.entities.Post;
import com.questApp.questApp.entities.User;
import com.questApp.questApp.repos.PostRepository;
import com.questApp.questApp.requests.PostCreateRequest;
import com.questApp.questApp.requests.PostUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public List<Post> getAllPosts(Optional<Long> userId) {
        if (userId.isPresent())
            return postRepository.findByUserId(userId.get());
        return postRepository.findAll();
    }

    public Post getOnePostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public Post createOnePost(PostCreateRequest newPostRequest) {
        User user = userService.getOneUserById(newPostRequest.getUserId());
        if (user == null)
            return null;
        Post postToBeSaved = new Post();
        postToBeSaved.setId(newPostRequest.getId());
        postToBeSaved.setText(newPostRequest.getText());
        postToBeSaved.setTitle(newPostRequest.getTitle());
        postToBeSaved.setUser(user);
        return postRepository.save(postToBeSaved);
    }

    public Post updateOnePostById(Long postId, PostUpdateRequest postUpdateRequest) {
        try {
            Optional<Post> post = postRepository.findById(postId);

            if (post.isPresent()) {
                Post foundPostToUpdate = post.get();
                foundPostToUpdate.setText(postUpdateRequest.getText());
                foundPostToUpdate.setTitle(postUpdateRequest.getTitle());
                postRepository.save(foundPostToUpdate);
                return foundPostToUpdate;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Optional<Post> deleteOnePostById(Long postId) {
        try {
            Optional<Post> post = postRepository.findById(postId);
            postRepository.deleteById(postId);
            return post;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
