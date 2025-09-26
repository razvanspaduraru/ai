package com.chat.ai.repository;

import com.chat.ai.models.db.Chat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatsRepository extends CrudRepository<Chat, Long> {
}
