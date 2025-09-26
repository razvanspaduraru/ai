package com.chat.ai.models.api.request;

import lombok.Data;

@Data
public class GenerateRecommendationRequest {
    private String userName;
    private String password;
}
