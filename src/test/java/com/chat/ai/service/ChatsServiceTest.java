package com.chat.ai.service;

import com.chat.ai.repository.ChatsRepository;
import com.chat.ai.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ChatsServiceTest {
    @Mock
    private ChatsRepository chatRepository;

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private ChatsService chatsService;

    @Test
    void testAddChat() {

    }
}

