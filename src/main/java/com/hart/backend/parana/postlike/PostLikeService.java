package com.hart.backend.parana.postlike;

import com.hart.backend.parana.post.Post;
import com.hart.backend.parana.post.PostService;
import com.hart.backend.parana.postlike.dto.CreatePostLikeDto;
import com.hart.backend.parana.postlike.request.CreatePostLikeRequest;
import com.hart.backend.parana.advice.BadRequestException;
import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;

    private final UserService userService;

    private final PostService postService;

    @Autowired
    public PostLikeService(
            PostLikeRepository postLikeRepository,
            UserService userService,
            PostService postService) {
        this.postLikeRepository = postLikeRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public boolean hasAlreadyLikedPost(Long userId, Long postId) {
        return this.postLikeRepository.hasAlreadyLikedPost(userId, postId);
    }

    public Long countPostLikes(Long postId) {
        return this.postLikeRepository.countByPostId(postId);
    }

    public CreatePostLikeDto createPostLike(CreatePostLikeRequest request) {

        if (hasAlreadyLikedPost(request.getUserId(), request.getPostId())) {
            throw new BadRequestException("You have already liked this post");
        }

        User user = this.userService.getUserById(request.getUserId());
        Post post = this.postService.getPostById(request.getPostId());

        this.postLikeRepository.save(new PostLike(user, post));

        return new CreatePostLikeDto(true);
    }

    private PostLike getPostLike(Long postId, Long userId) {
        return this.postLikeRepository.findPostLikeByPostIdAndUserId(postId, userId);
    }

    public void deletePostLike(Long postId, Long userId) {
        PostLike postLike = getPostLike(postId, userId);
        User currentUser = this.userService.getCurrentlyLoggedInUser();

        if (currentUser.getId() == userId) {
            this.postLikeRepository.delete(postLike);
        }
    }
}
