package com.hart.backend.parana.message;

import com.hart.backend.parana.message.dto.MessageDto;
import com.hart.backend.parana.message.request.CreateMessageRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageWebSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;

    @Autowired
    public MessageWebSocketController(SimpMessagingTemplate simpMessagingTemplate,
            MessageService messageService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageService = messageService;
    }

    @MessageMapping("private-message")
    public void receiveFriendRequest(@Payload CreateMessageRequest message) {
        Long id = this.messageService.createMessage(message);
        MessageDto messageEntity = this.messageService.getMessage(id);

        this.simpMessagingTemplate.convertAndSendToUser(String.valueOf(message.getSenderId()), "private",
                messageEntity);
        this.simpMessagingTemplate.convertAndSendToUser(String.valueOf(message.getReceiverId()), "private",
                messageEntity);
    }
}
