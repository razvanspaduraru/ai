package com.chat.ai.models.api.auth;

import lombok.Data;

@Data
public class AuthRequest {
    private String userName;
    private String password;
}
