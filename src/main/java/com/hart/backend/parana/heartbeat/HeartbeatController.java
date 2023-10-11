package com.hart.backend.parana.heartbeat;

import com.hart.backend.parana.heartbeat.response.GetHeartbeatResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/heartbeat")
public class HeartbeatController {

    @GetMapping("")
    public ResponseEntity<GetHeartbeatResponse> heartbeat() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GetHeartbeatResponse("heartbeat received"));
    }

}
