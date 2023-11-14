package com.hart.backend.parana.story;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import com.hart.backend.parana.advice.BadRequestException;
import com.hart.backend.parana.amazon.AmazonService;
import com.hart.backend.parana.story.dto.StoryDto;
import com.hart.backend.parana.story.request.CreateStoryRequest;
import com.hart.backend.parana.story.request.SendStoryRequest;
import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserService;
import com.hart.backend.parana.util.MyUtil;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StoryService {

    private final byte STORY_LIMIT = 5;
    private final int MAX_STORY_SECONDS = 86400;
    private final int MAX_MEGA_BYTES = 3;
    private final String BUCKET_NAME = "arrow-date/parana/stories";

    private final StoryRepository storyRepository;

    private final UserService userService;

    private final AmazonService amazonService;

    @Autowired
    public StoryService(
            StoryRepository storyRepository,
            UserService userService,
            AmazonService amazonService) {
        this.storyRepository = storyRepository;
        this.userService = userService;
        this.amazonService = amazonService;
    }

    public Long createExpiresIn() {
        return Instant.now().getEpochSecond();
    }

    private void validateFileSize(MultipartFile file) {
        if (file.getSize() > MAX_MEGA_BYTES * 1024 * 1024) {
            throw new BadRequestException("File cannot exceed 3MB");
        }
    }

    public Long createTextStory(SendStoryRequest story) {
        if (exceedStoryLimit(story.getUserId())) {
            throw new BadRequestException("You have exceeded the story limit for the day");
        }

        User user = this.userService.getUserById(story.getUserId());

        Story newStory = new Story();

        newStory.setUser(user);
        newStory.setExpiresIn(createExpiresIn());

        newStory.setText(Jsoup.clean(story.getText(), Safelist.none()));
        newStory.setFontSize(story.getFontSize());
        newStory.setDuration(story.getDuration());
        newStory.setColor(story.getColor());
        newStory.setBackground(story.getBackground());
        newStory.setAlignment(story.getAlignment());

        this.storyRepository.save(newStory);

        return newStory.getId();

    }

    public Long createPhotoStory(CreateStoryRequest story) {

        if (exceedStoryLimit(story.getUserId())) {
            throw new BadRequestException("You have exceeded the story limit for the day");
        }

        User user = this.userService.getUserById(story.getUserId());

        Story newStory = new Story();

        newStory.setUser(user);
        newStory.setExpiresIn(createExpiresIn());

        if (story.getFile() != null) {
            validateFileSize(story.getFile());

            String filename = this.amazonService.upload(BUCKET_NAME,
                    story.getFile().getOriginalFilename(),
                    story.getFile());
            Map<String, String> data = this.amazonService.getPublicUrl(BUCKET_NAME,
                    filename);

            newStory.setPhotoUrl(data.get("url"));
            newStory.setPhotoFileName(data.get("filename"));

            newStory.setDuration(story.getDuration());

        }
        this.storyRepository.save(newStory);

        return newStory.getId();
    }

    private boolean exceedStoryLimit(Long userId) {
        List<Story> stories = this.storyRepository.findTop5ByUserIdOrderByIdDesc(userId);

        if (stories.size() < STORY_LIMIT) {
            return false;
        }

        long earliestStoryInSeconds = MyUtil.getSeconds(stories.get(stories.size() - 1).getCreatedAt());
        long nowInSeconds = createExpiresIn();

        if (nowInSeconds - earliestStoryInSeconds > MAX_STORY_SECONDS) {
            return false;
        }

        return true;
    }

    public StoryDto getStory(Long storyId) {
        return this.storyRepository.getStory(storyId);
    }

    public List<StoryDto> getStories(Long userId) {
        return this.storyRepository.getStories(userId);
    }

    public void deleteExpiredStories(List<StoryDto> stories) {
        final int ONE_DAY = 86400;
        for (StoryDto story : stories) {
            if ((createExpiresIn() - story.getExpiresIn()) > ONE_DAY) {
                this.storyRepository.deleteById(story.getId());
            }
        }
    }
}
