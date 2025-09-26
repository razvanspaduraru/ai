package com.chat.ai.service;

import com.chat.ai.models.api.response.AddChatResponse;
import com.chat.ai.models.db.Chat;
import com.chat.ai.models.api.request.ChatRequest;
import com.chat.ai.models.db.User;
import com.chat.ai.repository.ChatsRepository;
import com.chat.ai.repository.UsersRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.retry.NonTransientAiException;
import org.springframework.stereotype.Service;

@Service
public class ChatsService {
    private final ChatsRepository chatsRepository;
    private final UsersRepository usersRepository;
    private final ChatClient.Builder chatClientBuilder;

    public ChatsService(ChatsRepository chatsRepository, UsersRepository usersRepository, ChatClient.Builder chatClientBuilder) {
        this.chatsRepository = chatsRepository;
        this.usersRepository = usersRepository;
        this.chatClientBuilder = chatClientBuilder;
    }

    private Chat getResponse(ChatRequest request) {
        ChatClient chatClient = chatClientBuilder.build();
        String response;
        try {
            response = chatClient.prompt()
                    .user(request.getMessage())
                    .call()
                    .content();
        } catch (NonTransientAiException e) {
            response = "Service is not available";
        }

        Chat chat = new Chat();
        chat.setMessage(request.getMessage());
        chat.setResponse(response);

        return chat;
    }

    public AddChatResponse addChat(ChatRequest request) {
        User user = usersRepository.findByUsername(request.getUserName());
        Chat chat = getResponse(request);
        chat.setUser(user);
        chatsRepository.save(chat);
        user.getChats().add(chat);
        usersRepository.save(user);

        return new AddChatResponse(chat.getMessage(), chat.getResponse());
    }
}
