package com.chat.ai.controller;

import com.chat.ai.models.api.response.AddChatResponse;
import com.chat.ai.models.db.Chat;
import com.chat.ai.models.api.request.ChatRequest;
import com.chat.ai.service.ChatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatsController {
    private final ChatsService chatsService;

    public ChatsController(ChatsService chatsService, PasswordEncoder passwordEncoder) {
        this.chatsService = chatsService;
    }

    @PostMapping("/add")
    public ResponseEntity<AddChatResponse> addChat(@RequestBody ChatRequest request) {
        AddChatResponse chat = chatsService.addChat(request);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }
}
