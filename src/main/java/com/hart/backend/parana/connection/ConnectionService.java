package com.hart.backend.parana.connection;

import com.hart.backend.parana.user.Role;
import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserService;
import com.hart.backend.parana.util.MyUtil;

import java.util.List;

import com.hart.backend.parana.advice.BadRequestException;
import com.hart.backend.parana.advice.NotFoundException;
import com.hart.backend.parana.advice.ForbiddenException;
import com.hart.backend.parana.connection.dto.ConnectionDto;
import com.hart.backend.parana.connection.dto.ConnectionPaginationDto;
import com.hart.backend.parana.connection.dto.ConnectionStatusDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ConnectionService {

    private final UserService userService;
    private final ConnectionRepository connectionRepository;

    @Autowired
    public ConnectionService(
            ConnectionRepository connectionRepository,
            UserService userService) {
        this.connectionRepository = connectionRepository;
        this.userService = userService;
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
            int pageSize, String direction) {
        if (userId == null || role == null) {
            throw new BadRequestException("userId and role must be present in the query string");
        }
        int currentPage = MyUtil.paginate(page, direction);

        Pageable paging = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());

        Page<ConnectionDto> result = Role.USER == role
                ? this.connectionRepository.getUserConnections(userId, false, paging)
                : this.connectionRepository.getTeacherConnections(userId, false, paging);

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

    public ConnectionPaginationDto<ConnectionDto> getConnections(Long userId, int page, int pageSize,
            String direction) {
        User currentUser = this.userService.getUserById(userId);
        int currentPage = MyUtil.paginate(page, direction);

        Pageable paging = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());

        Page<ConnectionDto> result = Role.USER == currentUser.getRole()
                ? this.connectionRepository.getUserConnections(userId, true, paging)
                : this.connectionRepository.getTeacherConnections(userId, true, paging);

        return new ConnectionPaginationDto<ConnectionDto>(
                result.getContent(),
                currentPage,
                pageSize,
                result.getTotalPages(),
                direction, result.getTotalElements());

    }
}
