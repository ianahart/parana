package com.hart.backend.parana.post;

import com.hart.backend.parana.connection.ConnectionService;
import com.hart.backend.parana.post.request.CreatePostRequest;
import com.hart.backend.parana.advice.ForbiddenException;
import com.hart.backend.parana.advice.BadRequestException;
import com.hart.backend.parana.amazon.AmazonService;
import com.hart.backend.parana.user.Role;
import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserService;
import com.hart.backend.parana.util.MyUtil;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PostService {

    private final int POST_LIMIT = 5;
    private final int POST_LIMIT_IN_SECONDS = 60 * 1;
    private final int MAX_MEGA_BYTES = 3;
    private final String BUCKET_NAME = "arrow-date/parana/posts";

    Logger logger = LoggerFactory.getLogger(PostService.class);

    private final UserService userService;

    private final ConnectionService connectionService;

    private final PostRepository postRepository;

    private AmazonService amazonService;

    @Autowired
    public PostService(UserService userService,
            PostRepository postRepository,
            ConnectionService connectionService,
            AmazonService amazonService) {
        this.userService = userService;
        this.postRepository = postRepository;
        this.connectionService = connectionService;
        this.amazonService = amazonService;
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
}
