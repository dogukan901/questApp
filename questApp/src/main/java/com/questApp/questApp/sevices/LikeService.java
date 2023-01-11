package com.questApp.questApp.sevices;

import com.questApp.questApp.entities.Comment;
import com.questApp.questApp.entities.Like;
import com.questApp.questApp.entities.Post;
import com.questApp.questApp.entities.User;
import com.questApp.questApp.repos.LikeRepository;
import com.questApp.questApp.requests.LikeCreateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService {

    private LikeRepository likeRepository;
    private UserService userService;
    private PostService postService;


    public LikeService(LikeRepository likeRepository, UserService userService, PostService postService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }


    public List<Like> getAllLikesWithParam(Optional<Long> userId, Optional<Long> postId) {
        if (postId.isPresent() && userId.isPresent())
            return likeRepository.findByUserIdAndPostId(userId.get(), postId.get());
        else if (userId.isPresent())
            return likeRepository.findByUserId(userId.get());
        else if (postId.isPresent())
            return likeRepository.findByPostId(postId.get());
        else
            return likeRepository.findAll();
    }

    public Like getOneLikeById(Long likeId) {
        return likeRepository.findById(likeId).orElse(null);
    }

    public Like createOneLike(LikeCreateRequest request) {
        User user = userService.getOneUserById(request.getUserId());
        Post post = postService.getOnePostById(request.getPostId());
        if(user != null && post != null) {
            Like likeToSave = new Like();
            likeToSave.setId(request.getId());
            likeToSave.setPost(post);
            likeToSave.setUser(user);
            return likeRepository.save(likeToSave);
        }else
            return null;
    }

    public void deleteOneLikeById(Long likeId) {
        try {
            Optional<Like> like = likeRepository.findById(likeId);
            likeRepository.deleteById(likeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }


}
