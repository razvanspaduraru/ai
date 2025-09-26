package com.chat.ai.repository;

import com.chat.ai.models.db.User;

public interface UsersRepository extends org.springframework.data.repository.CrudRepository<User, Long> {
    User findByUsernameAndPassword(String userName, String password);

    User findByUsername(String username);
}
