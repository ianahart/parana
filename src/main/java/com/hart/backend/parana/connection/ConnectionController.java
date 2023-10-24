package com.hart.backend.parana.connection;

import com.hart.backend.parana.connection.request.CreateConnectionRequest;
import com.hart.backend.parana.connection.request.UpdateConnectionRequest;
import com.hart.backend.parana.connection.response.CreateConnectionResponse;
import com.hart.backend.parana.connection.response.DeleteConnectionResponse;
import com.hart.backend.parana.connection.response.GetConnectionRequestResponse;
import com.hart.backend.parana.connection.response.GetConnectionResponse;
import com.hart.backend.parana.connection.response.GetConnectionStatusResponse;
import com.hart.backend.parana.connection.response.UpdateConnectionResponse;
import com.hart.backend.parana.user.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/connections")
public class ConnectionController {

    private final ConnectionService connectionService;

    @Autowired
    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @GetMapping("/status")
    public ResponseEntity<GetConnectionStatusResponse> getConnectionStatus(
            @RequestParam("senderId") Long senderId,
            @RequestParam("receiverId") Long receiverId) {

        return ResponseEntity.status(HttpStatus.OK).body(new GetConnectionStatusResponse("success",
                this.connectionService.getConnectionStatus(senderId, receiverId)));
    }

    @PostMapping("")
    public ResponseEntity<CreateConnectionResponse> createConnection(@RequestBody CreateConnectionRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateConnectionResponse("success",
                        this.connectionService.createConnection(request.getSenderId(), request.getReceiverId())));
    }

    @GetMapping("/requests")
    public ResponseEntity<GetConnectionRequestResponse> getConnectionRequests(
            @RequestParam("userId") Long userId,
            @RequestParam("role") Role role,
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("direction") String direction) {
        return ResponseEntity.status(HttpStatus.OK).body(new GetConnectionRequestResponse("success",
                this.connectionService.getConnectionRequests(userId, role, page, pageSize, direction)));
    }

    @DeleteMapping("/{connectionId}")
    public ResponseEntity<DeleteConnectionResponse> deleteConnection(@PathVariable("connectionId") Long connectionId) {
        this.connectionService.deleteConnection(connectionId);
        return ResponseEntity.status(HttpStatus.OK).body(new DeleteConnectionResponse("success"));

    }

    @PatchMapping("/{connectionId}")
    public ResponseEntity<UpdateConnectionResponse> updateConnection(@PathVariable("connectionId") Long connectionId,
            @RequestBody UpdateConnectionRequest request) {

        this.connectionService.confirmConnectionRequest(connectionId, request.getCurrentUserId());
        return ResponseEntity.status(HttpStatus.OK).body(new UpdateConnectionResponse("success"));
    }

    @GetMapping("")
    public ResponseEntity<GetConnectionResponse> getConnections(
            @RequestParam("userId") Long userId,
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("direction") String direction) {

        return ResponseEntity.status(HttpStatus.OK).body(new GetConnectionResponse("success",
                this.connectionService.getConnections(userId, page, pageSize, direction)));
    }
}
