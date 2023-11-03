package com.hart.backend.parana.message;

import com.hart.backend.parana.message.dto.MessageDto;
import com.hart.backend.parana.message.request.CreateMessageRequest;
import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserService;
import com.hart.backend.parana.util.MyUtil;

import java.util.List;

import com.hart.backend.parana.advice.BadRequestException;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    private final UserService userService;

    @Autowired
    public MessageService(MessageRepository messageRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    public MessageDto getMessage(Long messageId) {
        return this.messageRepository.getMessage(messageId);
    }

    public Long createMessage(CreateMessageRequest request) {
        User sender = this.userService.getUserById(request.getSenderId());
        User receiver = this.userService.getUserById(request.getReceiverId());

        if (request.getText().length() == 0 || request.getText().length() > 200) {
            throw new BadRequestException("Message must be between 1 and 200 characters");
        }

        Message message = new Message(
                Jsoup.clean(request.getText(), Safelist.none()),
                sender,
                receiver);

        this.messageRepository.save(message);
        return message.getId();
    }

    private void addReadableDate(List<MessageDto> messages) {
        for (MessageDto message : messages) {
            message.setReadableDate(MyUtil.createDate(message.getCreatedAt()));
        }
    }

    public List<MessageDto> getMessages(Long userId, Long connectionUserId) {
        Pageable pageable = PageRequest.of(0, 50, Sort.by("id").descending());
        Page<MessageDto> result = this.messageRepository.getMessages(userId, connectionUserId, pageable);

        addReadableDate(result.getContent());

        return result.getContent();
    }
}
