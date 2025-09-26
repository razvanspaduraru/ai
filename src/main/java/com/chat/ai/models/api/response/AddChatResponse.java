package com.chat.ai.models.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddChatResponse {
    private String message;
    private String response;
}
