package com.chat.ai.models.db;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "chats")
@NoArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    private String response;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
