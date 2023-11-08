package com.hart.backend.parana.post;

import com.hart.backend.parana.connection.ConnectionService;
import com.hart.backend.parana.connection.dto.MinimalConnectionDto;
import com.hart.backend.parana.post.dto.EditPostDto;
import com.hart.backend.parana.post.dto.PostDto;
import com.hart.backend.parana.post.dto.PostPaginationDto;
import com.hart.backend.parana.post.request.CreatePostRequest;
import com.hart.backend.parana.post.request.UpdatePostRequest;
import com.hart.backend.parana.postlike.PostLikeService;
import com.hart.backend.parana.privacy.PrivacyService;
import com.hart.backend.parana.advice.ForbiddenException;
import com.hart.backend.parana.advice.BadRequestException;
import com.hart.backend.parana.advice.NotFoundException;
import com.hart.backend.parana.amazon.AmazonService;
import com.hart.backend.parana.comment.CommentService;
import com.hart.backend.parana.user.Role;
import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserService;
import com.hart.backend.parana.util.MyUtil;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PostService {

    private final int POST_LIMIT = 5;
    private final int POST_LIMIT_IN_SECONDS = 60 * 5;
    private final int MAX_MEGA_BYTES = 3;
    private final String BUCKET_NAME = "arrow-date/parana/posts";

    Logger logger = LoggerFactory.getLogger(PostService.class);

    private final UserService userService;

    private final ConnectionService connectionService;

    private final PostRepository postRepository;

    private AmazonService amazonService;

    private final PostLikeService postLikeService;

    private final CommentService commentService;

    private final PrivacyService privacyService;

    @Autowired
    public PostService(UserService userService,
            PostRepository postRepository,
            ConnectionService connectionService,
            AmazonService amazonService,
            @Lazy PostLikeService postLikeService,
            @Lazy CommentService commentService,
            PrivacyService privacyService) {
        this.userService = userService;
        this.postRepository = postRepository;
        this.connectionService = connectionService;
        this.amazonService = amazonService;
        this.postLikeService = postLikeService;
        this.commentService = commentService;
        this.privacyService = privacyService;
    }

    public PostPaginationDto<PostDto> getPostsFeed(Long userId, int page, int pageSize, String direction) {
        List<Long> connectionIds = this.connectionService.getAllUserConnections(userId);
        int currentPage = MyUtil.paginate(page, direction);

        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());
        Page<PostDto> result = this.postRepository.getPostsFeed(pageable, connectionIds);

        addDatesToPosts(result.getContent());
        addNonConstructorFields(result.getContent());

        return new PostPaginationDto<PostDto>(result.getContent(), currentPage, pageSize,
                result.getTotalPages(),
                direction, result.getTotalElements());

    }

    private boolean canCreatePost(User author, User owner) {
        return this.connectionService.isConnected(author.getId(), owner.getId()) || author.getRole() == Role.TEACHER;
    }

    private void validateFileSize(MultipartFile file) {
        if (file.getSize() > MAX_MEGA_BYTES * 1024 * 1024) {
            throw new BadRequestException("File cannot exceed 3MB");
        }
    }

    public boolean exceedsPostLimit(Long authorId, long ownerId) {

        List<Post> posts = this.postRepository.findTop5ByAuthorIdAndOwnerIdOrderByIdDesc(authorId, ownerId);

        if (posts.size() < POST_LIMIT) {
            return true;
        }

        long earliestPostInSeconds = MyUtil.getSeconds(posts.get(posts.size() - 1).getCreatedAt());
        long nowInSeconds = Instant.now().getEpochSecond();

        if (nowInSeconds - earliestPostInSeconds > POST_LIMIT_IN_SECONDS) {
            return true;
        }

        return false;
    }

    public void createPost(CreatePostRequest request) {
        User author = this.userService.getUserById(request.getAuthorId());
        User owner = this.userService.getUserById(request.getOwnerId());

        if (this.privacyService.blockedFromCreatingPosts(author.getId(), owner.getId())) {
            throw new ForbiddenException("You are blocked from creating posts for this teacher");
        }

        if (!canCreatePost(author, owner)) {
            throw new ForbiddenException("Do not have necessary privileges. Try connecting.");
        }

        if (!exceedsPostLimit(request.getAuthorId(), request.getOwnerId())) {
            throw new BadRequestException("We only allow 5 posts per 5 minutes on a person's wall");
        }

        Post post = new Post();
        post.setIsEdited(false);
        post.setText(Jsoup.clean(request.getText(), Safelist.none()));
        post.setGif(request.getGif());

        if (request.getFile() != null) {
            validateFileSize(request.getFile());

            String filename = this.amazonService.upload(BUCKET_NAME, request.getFile().getOriginalFilename(),
                    request.getFile());
            Map<String, String> data = this.amazonService.getPublicUrl(BUCKET_NAME, filename);

            post.setFilename(data.get("filename"));
            post.setFileUrl(data.get("url"));

        }

        post.setOwner(owner);
        post.setAuthor(author);

        this.postRepository.save(post);

    }

    private Page<PostDto> paginatePosts(Long ownerId, int page, int pageSize, String direction, Boolean filtered,
            int year, int month) {
        int currentPage = MyUtil.paginate(page, direction);
        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());

        if (filtered) {
            LocalDateTime StartDate = LocalDateTime.of(year, month + 1, 1, 23, 59, 59, 0);
            LocalDateTime endDate = LocalDateTime.of(year, month + 1, 30, 23, 59, 59, 0);

            Timestamp startTimestamp = Timestamp.valueOf(StartDate);
            Timestamp endTimestamp = Timestamp.valueOf(endDate);

            return this.postRepository.getFilteredPosts(ownerId, startTimestamp, endTimestamp, pageable);
        }

        return this.postRepository.getPosts(ownerId, pageable);
    }

    private void addDatesToPosts(List<PostDto> posts) {
        for (PostDto post : posts) {
            post.setReadableDate(MyUtil.createDate(post.getCreatedAt()));
        }
    }

    private void addNonConstructorFields(List<PostDto> posts) {
        User user = this.userService.getCurrentlyLoggedInUser();
        for (PostDto post : posts) {
            post.setCurrentUserHasLikedPost(
                    this.postLikeService.hasAlreadyLikedPost(user.getId(), post.getId()));

            post.setLikesCount(this.postLikeService.countPostLikes(post.getId()));
            post.setComment(this.commentService.getLatestComment(post.getId()));
            post.setCommentCount(this.commentService.getCommentCount(post.getId()));
        }
    }

    public PostPaginationDto<PostDto> getFilteredPosts(Long ownerId, int year, int month, int page, int pageSize,
            String direction) {

        Page<PostDto> result = paginatePosts(ownerId, page, pageSize, direction, true, year, month);

        addDatesToPosts(result.getContent());
        addNonConstructorFields(result.getContent());

        return new PostPaginationDto<PostDto>(result.getContent(), page + 1, pageSize,
                result.getTotalPages(),
                direction, result.getTotalElements());
    }

    public PostPaginationDto<PostDto> getPosts(Long ownerId, int page, int pageSize, String direction) {

        Page<PostDto> result = paginatePosts(ownerId, page, pageSize, direction, false, 0, 0);

        addDatesToPosts(result.getContent());
        addNonConstructorFields(result.getContent());

        return new PostPaginationDto<PostDto>(result.getContent(), page + 1, pageSize,
                result.getTotalPages(),
                direction, result.getTotalElements());
    }

    public Post getPostById(Long postId) {
        return this.postRepository.findById(postId).orElseThrow(() -> {
            String error = String.format("A post with the id %d was not found", postId);
            logger.info(error);
            throw new NotFoundException(error);
        });
    }

    private boolean canModifyPost(User currentUser, Post post) {
        return currentUser.getId() == post.getAuthor().getId() || currentUser.getId() == post.getOwner().getId();
    }

    public void deletePost(Long postId) {
        Post post = getPostById(postId);
        User currentUser = this.userService.getCurrentlyLoggedInUser();

        if (!canModifyPost(currentUser, post)) {
            throw new ForbiddenException("You do not have necessary privileges to delete this post");
        }

        if (post.getFilename() != null) {
            this.amazonService.delete(BUCKET_NAME, post.getFilename());
        }

        this.postRepository.delete(post);
    }

    public EditPostDto getPost(Long postId) {
        return this.postRepository.getPost(postId);
    }

    public void updatePost(Long postId, UpdatePostRequest request) {
        Post post = getPostById(postId);
        User currentUser = this.userService.getCurrentlyLoggedInUser();

        if (this.privacyService.blockedFromCreatingPosts(post.getAuthor().getId(), post.getOwner().getId())) {
            throw new ForbiddenException("You are blocked from editing posts for this teacher");
        }

        if (currentUser.getId() != post.getAuthor().getId()) {
            throw new ForbiddenException("Cannot edit another user's post");
        }

        post.setIsEdited(true);
        post.setText(Jsoup.clean(request.getText(), Safelist.none()));
        post.setGif(request.getGif());

        if (post.getFilename() != null && request.getFile() == null) {
            post.setFilename(null);
            post.setFileUrl(null);
        }

        if (request.getFile() != null) {
            validateFileSize(request.getFile());

            String filename = this.amazonService.upload(BUCKET_NAME, request.getFile().getOriginalFilename(),
                    request.getFile());
            Map<String, String> data = this.amazonService.getPublicUrl(BUCKET_NAME, filename);

            if (post.getFilename() != null) {
                this.amazonService.delete(BUCKET_NAME, post.getFilename());
            }

            post.setFilename(data.get("filename"));
            post.setFileUrl(data.get("url"));

        }

        this.postRepository.save(post);
    }
}
