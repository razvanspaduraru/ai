package com.chat.ai.service;

import com.chat.ai.models.api.request.CreateUserRequest;
import com.chat.ai.models.api.response.CreateUserResponse;
import com.chat.ai.models.db.User;
import com.chat.ai.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {
    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UsersService usersService;

    @Test
    public void addUserTest() {
        CreateUserRequest request = new CreateUserRequest();
        request.setName("rpaduraru");
        request.setEmail("rpaduraru@gmail.com");
        request.setPassword("password");

        User savedUser = new User();
        savedUser.setUsername("rpaduraru");
        savedUser.setEmail("rpaduraru@gmail.com");
        when(usersRepository.save(any(User.class))).thenReturn(savedUser);

        CreateUserResponse response = usersService.createUser(request);

        assertEquals("User created successfully", response.getMessage());
        assertEquals("rpaduraru", response.getUserName());
    }
}
