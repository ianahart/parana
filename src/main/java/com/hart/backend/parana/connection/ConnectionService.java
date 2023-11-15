package com.hart.backend.parana.connection;

import com.hart.backend.parana.user.Role;
import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserService;
import com.hart.backend.parana.util.MyUtil;

import java.util.List;
import java.util.ArrayList;

import com.hart.backend.parana.advice.BadRequestException;
import com.hart.backend.parana.advice.NotFoundException;
import com.hart.backend.parana.advice.ForbiddenException;
import com.hart.backend.parana.connection.dto.ActiveConnectionDto;
import com.hart.backend.parana.connection.dto.ConnectionDto;
import com.hart.backend.parana.connection.dto.ConnectionPaginationDto;
import com.hart.backend.parana.connection.dto.ConnectionStatusDto;
import com.hart.backend.parana.connection.dto.ConnectionStoryDto;
import com.hart.backend.parana.connection.dto.MinimalConnectionDto;
import com.hart.backend.parana.privacy.Privacy;
import com.hart.backend.parana.privacy.PrivacyService;
import com.hart.backend.parana.story.Story;
import com.hart.backend.parana.story.StoryService;
import com.hart.backend.parana.story.dto.StoryDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ConnectionService {

    Logger logger = LoggerFactory.getLogger(ConnectionService.class);

    private final UserService userService;
    private final ConnectionRepository connectionRepository;
    private final PrivacyService privacyService;
    private final StoryService storyService;

    @Autowired
    public ConnectionService(
            ConnectionRepository connectionRepository,
            UserService userService,
            PrivacyService privacyService,
            StoryService storyService) {
        this.connectionRepository = connectionRepository;
        this.userService = userService;
        this.privacyService = privacyService;
        this.storyService = storyService;
    }

    public void checkUsersArePresent(Long senderId, Long receiverId) {
        if (senderId == null || receiverId == null) {
            throw new BadRequestException("senderId or receiverId is null");
        }
    }

    public boolean duplicateConnection(Long senderId, Long receiverId) {
        checkUsersArePresent(senderId, receiverId);
        return this.connectionRepository.duplicateConnection(senderId, receiverId);
    }

    public ConnectionStatusDto createConnection(Long senderId, Long receiverId) {
        if (this.duplicateConnection(senderId, receiverId)) {
            throw new BadRequestException("You have already sent a request");
        }

        User sender = this.userService.getUserById(senderId);
        User receiver = this.userService.getUserById(receiverId);

        Connection connection = new Connection(sender, receiver, true, false);
        this.connectionRepository.save(connection);

        String status = connection.getPending() ? "Pending request..." : "Request a lesson";

        return new ConnectionStatusDto(status, connection.getAccepted(), connection.getPending());
    }

    private ConnectionStatusDto connectionStatus(Connection connection) {
        String status = "";

        if (connection == null) {

            status = "Request a lesson";
            return new ConnectionStatusDto(status, false, false);

        } else if (connection.getPending() && !connection.getAccepted()) {

            status = "Pending request...";
        } else if (!connection.getPending() && connection.getAccepted()) {

            status = "Connected";
        }

        return new ConnectionStatusDto(status, connection.getAccepted(), connection.getPending());

    }

    public ConnectionStatusDto getConnectionStatus(Long senderId, long receiverId) {
        checkUsersArePresent(senderId, receiverId);
        Connection connection = this.connectionRepository.getConnectionBySenderAndReceiver(senderId, receiverId);

        return connectionStatus(connection);
    }

    private List<ConnectionDto> packageConnectionRequests(List<ConnectionDto> connectionRequests) {

        for (ConnectionDto connectionRequest : connectionRequests) {

            connectionRequest.setReadableDate(MyUtil.constructReadableDate(connectionRequest.getCreatedAt()));
        }

        return connectionRequests;
    }

    public ConnectionPaginationDto<ConnectionDto> getConnectionRequests(Long userId, Role role, int page,
            int pageSize, String direction, String searchTerm) {
        if (userId == null || role == null) {
            throw new BadRequestException("userId and role must be present in the query string");
        }

        String query = searchTerm.length() == 0 ? null : searchTerm;

        int currentPage = MyUtil.paginate(page, direction);

        Pageable paging = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());

        Page<ConnectionDto> result = Role.USER == role
                ? this.connectionRepository.getUserConnections(userId, false, query, paging)
                : this.connectionRepository.getTeacherConnections(userId, false, query, paging);

        List<ConnectionDto> connectionRequests = packageConnectionRequests(result.getContent());

        return new ConnectionPaginationDto<ConnectionDto>(
                connectionRequests,
                currentPage,
                pageSize,
                result.getTotalPages(),
                direction, result.getTotalElements());

    }

    public Connection getConnectionById(Long connectionId) {
        return this.connectionRepository.findById(connectionId).orElseThrow(
                () -> new NotFoundException(String.format("A connection with the id %d was not found", connectionId)));
    }

    private boolean canModifyConnection(User currentUser, Connection connection) {
        return currentUser.getId() == connection.getReceiver().getId()
                || currentUser.getId() == connection.getSender().getId();
    }

    public void deleteConnection(Long connectionId) {
        Connection connection = getConnectionById(connectionId);
        User currentUser = this.userService.getCurrentlyLoggedInUser();

        if (!canModifyConnection(currentUser, connection)) {
            throw new ForbiddenException("Cannot modify another user's requests");
        }
        this.connectionRepository.delete(connection);
    }

    public void confirmConnectionRequest(Long connectionId, Long currentUserId) {
        Connection connection = getConnectionById(connectionId);
        User currentUser = this.userService.getCurrentlyLoggedInUser();

        if (canModifyConnection(currentUser, connection) && currentUser.getRole() == Role.TEACHER) {
            connection.setPending(false);
            connection.setAccepted(true);

            this.connectionRepository.save(connection);
        }
    }

    private List<ConnectionDto> filterOutBlockedConnections(List<ConnectionDto> connections, List<Long> ids) {
        return connections.stream().filter(connection -> !ids.contains(connection.getUserId())).toList();
    }

    public ConnectionPaginationDto<ConnectionDto> getConnections(Long userId, int page, int pageSize,
            String direction, String searchTerm) {
        User currentUser = this.userService.getUserById(userId);
        int currentPage = MyUtil.paginate(page, direction);

        String query = searchTerm.length() == 0 ? null : searchTerm.toLowerCase();

        Pageable paging = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());

        Page<ConnectionDto> result = Role.USER == currentUser.getRole()
                ? this.connectionRepository.getUserConnections(userId, true, query, paging)
                : this.connectionRepository.getTeacherConnections(userId, true, query, paging);

        List<ConnectionDto> connections = new ArrayList<>();
        if (Role.USER == currentUser.getRole()) {
            List<Long> ids = this.privacyService.getBlockedUserPrivaciesMessages(currentUser);
            connections = filterOutBlockedConnections(result.getContent(), ids);

        } else {
            List<Long> ids = this.privacyService.getBlockedByUserPrivaciesMessages(currentUser);
            connections = filterOutBlockedConnections(result.getContent(), ids);
        }

        return new ConnectionPaginationDto<ConnectionDto>(
                connections,
                currentPage,
                pageSize,
                result.getTotalPages(),
                direction, result.getTotalElements());

    }

    public boolean isConnected(Long senderId, Long receiverId) {
        if (senderId == null || receiverId == null) {
            logger.info("isConnected is missing senderId or receiverId or both");
            throw new BadRequestException("is Connected is missing parameters");
        }
        return this.connectionRepository.isConnected(senderId, receiverId);
    }

    public boolean isPending(Long senderId, Long receiverId) {
        if (senderId == null || receiverId == null) {
            logger.info("isPending is missing senderId or receiverId or both");
            throw new BadRequestException("is pending is missing parameters");
        }
        return this.connectionRepository.isPending(senderId, receiverId);
    }

    public List<Long> getAllUserConnections(Long userId) {
        List<Long> connections = this.connectionRepository.getAllUserConnections(userId, true)
                .stream()
                .map(v -> v.getUserId()).toList();

        return connections;
    }

    public List<ActiveConnectionDto> getActiveTeacherConnections(Long userId) {
        return this.connectionRepository.getActiveTeacherConnections(userId, true);
    }

    public List<ActiveConnectionDto> getActiveUserConnections(Long userId) {
        return this.connectionRepository.getActiveUserConnections(userId, true);
    }

    private Page<ConnectionDto> paginateConnections(Long userId, int page, int pageSize, String direction) {
        User user = this.userService.getUserById(userId);
        int currentPage = MyUtil.paginate(page, direction);
        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());
        return Role.USER == user.getRole()
                ? this.connectionRepository.getUserConnections(userId, true, null, pageable)
                : this.connectionRepository.getTeacherConnections(userId, true, null, pageable);

    }

    private List<ConnectionStoryDto> packageConnectionStories(List<ConnectionDto> connections) {
        User currentUser = this.userService.getCurrentlyLoggedInUser();
        List<ConnectionStoryDto> connectionStories = new ArrayList<>();

        List<StoryDto> currentUserStories = this.storyService.getStories(currentUser.getId());
        if (currentUserStories.size() > 0) {
            connectionStories.add(new ConnectionStoryDto(currentUserStories,
                    currentUser.getId(), currentUser.getFullName(), currentUser.getProfile().getAvatarUrl()));

        }

        for (ConnectionDto c : connections) {
            List<StoryDto> stories = this.storyService.getStories(c.getUserId());
            this.storyService.deleteExpiredStories(stories);

            if (stories.size() > 0) {
                connectionStories
                        .add(new ConnectionStoryDto(stories, c.getUserId(), c.getFullName(),
                                c.getAvatarUrl()));
            }
        }

        return connectionStories;
    }

    public ConnectionPaginationDto<ConnectionStoryDto> getConnectionStories(Long userId, int page, int pageSize,
            String direction) {
        Page<ConnectionDto> result = paginateConnections(userId, page, pageSize, direction);

        List<ConnectionStoryDto> connectionStories = packageConnectionStories(result.getContent());

        return new ConnectionPaginationDto<ConnectionStoryDto>(
                connectionStories,
                result.getNumber(),
                pageSize,
                result.getTotalPages(),
                direction,
                result.getTotalElements());

    }
}
