package com.hart.backend.parana.story;

import java.util.List;

import com.hart.backend.parana.connection.ConnectionService;
import com.hart.backend.parana.connection.dto.ActiveConnectionDto;
import com.hart.backend.parana.story.dto.StoryDto;
import com.hart.backend.parana.story.request.SendStoryRequest;
import com.hart.backend.parana.user.Role;
import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class StoryWebSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final StoryService storyService;

    private final ConnectionService connectionService;

    private final UserService userService;

    @Autowired
    public StoryWebSocketController(
            SimpMessagingTemplate simpMessagingTemplate,
            StoryService storyService,
            ConnectionService connectionService,
            UserService userService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.storyService = storyService;
        this.connectionService = connectionService;
        this.userService = userService;
    }

    @MessageMapping("private-stories")
    public void receiveStory(@Payload SendStoryRequest story) {
        Long storyId = this.storyService.createTextStory(story);
        User currentUser = this.userService.getUserById(story.getUserId());
        StoryDto storyEntity = this.storyService.getStory(storyId);

        List<ActiveConnectionDto> activeConnections = currentUser.getRole() == Role.USER
                ? this.connectionService.getActiveUserConnections(story.getUserId())
                : this.connectionService.getActiveTeacherConnections(story.getUserId());

        for (ActiveConnectionDto activeConnection : activeConnections) {
            this.simpMessagingTemplate.convertAndSendToUser(String.valueOf(activeConnection.getUserId()), "stories",
                    storyEntity);
        }
        this.simpMessagingTemplate.convertAndSendToUser(String.valueOf(story.getUserId()), "stories",
                storyEntity);

    }
}
