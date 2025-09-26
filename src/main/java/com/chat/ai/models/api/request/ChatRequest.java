package com.chat.ai.models.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatRequest {
    private String userName;
    private String password;
    private String message;
}
