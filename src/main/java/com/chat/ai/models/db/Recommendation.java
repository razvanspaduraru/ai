package com.chat.ai.models.db;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "recommendations")
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
