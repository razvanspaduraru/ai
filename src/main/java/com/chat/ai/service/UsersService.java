package com.chat.ai.service;

import com.chat.ai.models.api.request.CreateUserRequest;
import com.chat.ai.models.api.response.CreateUserResponse;
import com.chat.ai.models.db.User;
import com.chat.ai.repository.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UsersService implements UserDetailsService {
    private final UsersRepository repository;

    public UsersService(UsersRepository repository) {
        this.repository = repository;
    }

    public CreateUserResponse createUser(CreateUserRequest request) {
        User user = new User();
        user.setUsername(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        String message = "User created successfully";
        try {
            repository.save(user);
        } catch (Exception e) {
            message = "User already exists";
        }

        return new CreateUserResponse(request.getName(), message);
    }


    public UserDetails loadUserByUsername(String username) {
        return repository.findByUsername(username) != null ? repository.findByUsername(username) : null;
    }

    public boolean getById(Long id) {
        return repository.existsById(id);
    }
}
