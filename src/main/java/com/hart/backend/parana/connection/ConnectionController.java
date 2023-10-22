package com.hart.backend.parana.connection;

import com.hart.backend.parana.connection.request.CreateConnectionRequest;
import com.hart.backend.parana.connection.response.CreateConnectionResponse;
import com.hart.backend.parana.connection.response.GetConnectionStatusResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

}
