package com.hart.backend.parana.connection;

import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserService;
import com.hart.backend.parana.advice.BadRequestException;
import com.hart.backend.parana.connection.dto.ConnectionStatusDto;

import org.springframework.beans.factory.annotation.Autowired;
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

            status = "Connected...";
        }

        return new ConnectionStatusDto(status, connection.getAccepted(), connection.getPending());

    }

    public ConnectionStatusDto getConnectionStatus(Long senderId, long receiverId) {
        checkUsersArePresent(senderId, receiverId);
        Connection connection = this.connectionRepository.getConnectionBySenderAndReceiver(senderId, receiverId);

        return connectionStatus(connection);
    }
}
