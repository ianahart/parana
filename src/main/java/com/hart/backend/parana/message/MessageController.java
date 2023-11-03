package com.hart.backend.parana.message;

import com.hart.backend.parana.message.request.CreateMessageRequest;
import com.hart.backend.parana.message.response.CreateMessageResponse;
import com.hart.backend.parana.message.response.GetMessageResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("")
    public ResponseEntity<CreateMessageResponse> createMessage(@RequestBody CreateMessageRequest request) {
        this.messageService.createMessage(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CreateMessageResponse("success"));
    }

    @GetMapping("")
    public ResponseEntity<GetMessageResponse> getMessages(
            @RequestParam("userId") Long userId,
            @RequestParam("connectionUserId") Long connectionUserId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GetMessageResponse("success", this.messageService.getMessages(userId, connectionUserId)));
    }
}
