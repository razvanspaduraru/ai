package com.chat.ai.controller;

import com.chat.ai.models.api.request.GenerateRecommendationRequest;
import com.chat.ai.models.api.response.GenerateRecommendationResponse;
import com.chat.ai.service.RecommendationsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recommendation")
public class RecommendationsController {
    public final RecommendationsService recommendationsService;

    public RecommendationsController(RecommendationsService recommendationsService) {
        this.recommendationsService = recommendationsService;
    }

    @PostMapping("/getRecommendationForUser")
    public ResponseEntity<GenerateRecommendationResponse> getRecommendationForUser(@RequestBody GenerateRecommendationRequest request) {
        GenerateRecommendationResponse response = recommendationsService.generateRecommendations(request);
        return new ResponseEntity<>(response, org.springframework.http.HttpStatus.OK);
    }
}
