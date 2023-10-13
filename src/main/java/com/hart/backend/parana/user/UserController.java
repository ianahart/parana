package com.hart.backend.parana.user;

import com.hart.backend.parana.user.response.SyncUserResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sync")
    public ResponseEntity<SyncUserResponse> syncUser(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SyncUserResponse("success", this.userService.syncUser(request)));
    }
}
