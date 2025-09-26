package com.chat.ai.service;

import com.chat.ai.models.api.request.GenerateRecommendationRequest;
import com.chat.ai.models.api.response.GenerateRecommendationResponse;
import com.chat.ai.models.db.Chat;
import com.chat.ai.models.db.Recommendation;
import com.chat.ai.models.db.User;
import com.chat.ai.repository.RecommendationsRepository;
import com.chat.ai.repository.UsersRepository;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RecommendationsService {
    private final RecommendationsRepository recommendationsRepository;
    private final UsersRepository usersRepository;
    private final org.springframework.ai.chat.client.ChatClient.Builder chatClientBuilder;

    public RecommendationsService(
            RecommendationsRepository repository,
            UsersRepository usersRepository,
            org.springframework.ai.chat.client.ChatClient.Builder chatClientBuilder) {
        this.recommendationsRepository = repository;
        this.usersRepository = usersRepository;
        this.chatClientBuilder = chatClientBuilder;
    }

    private String generateRecommendationsWithAI(Map<String, String> messageToResponse) {
        org.springframework.ai.chat.client.ChatClient chatClient = chatClientBuilder.build();
        if (messageToResponse.size() != 3) {
            return "Error: Exactly 3 question-response pairs are required.";
        }

        // Create the system prompt
        String systemPrompt =
                "You are a recommendation system analyzing user responses to generate personalized advice. " +
                        "Provide a detailed, helpful recommendation based on the following question-answer pairs. " +
                        "Consider patterns in the answers, analyze the user's preferences and needs, " +
                        "and offer specific, actionable advice. Keep the tone professional and supportive.";

        // Format user content with questions and responses
        StringBuilder userContent = new StringBuilder("Here are the user's responses:\n\n");
        messageToResponse.forEach((question, response) -> {
            userContent.append("Question: ").append(question).append("\n");
            userContent.append("Response: ").append(response).append("\n\n");
        });

        Message userMessage = new UserMessage(userContent.toString());
        Message systemMessage = new SystemMessage(systemPrompt);

        // Create the prompt with both system and user messages
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));

        try {
            return chatClient.prompt(prompt).call().content();
        } catch (Exception e) {
            return "Error generating recommendation, an error occurred.";
        }
    }

    public GenerateRecommendationResponse generateRecommendations(GenerateRecommendationRequest request) {
        if (request.getUserName() == null || request.getPassword() == null) {
            return new GenerateRecommendationResponse("Error: User name and password are required.");
        } else {
            User user = usersRepository.findByUsername(request.getUserName());
            if (user == null) {
                return new GenerateRecommendationResponse("Error: Invalid user name or password.");
            } else if (user.getChats() != null && user.getChats().size() >= 3) {
                Map<String, String> messageToResponse = user.getChats().reversed().stream().
                        limit(3)  // Take only the last 3 chats
                        .collect(Collectors.toMap(
                                Chat::getMessage,   // Key: question
                                Chat::getResponse,   // Value: response
                                (existing, _) -> existing,  // Keep first in case of duplicates
                                LinkedHashMap::new   // Maintain insertion order
                        ));
                String recommendation = generateRecommendationsWithAI(messageToResponse);

                persistRecommendation(user, recommendation);

                return new GenerateRecommendationResponse(recommendation);
            } else {
                return new GenerateRecommendationResponse("Error: Not enough chats to generate recommendation.");
            }
        }
    }

    private void persistRecommendation(User user, String recommendation) {
        Recommendation recommendationEntity = new Recommendation();
        recommendationEntity.setUser(user);
        recommendationEntity.setMessage(recommendation);
        recommendationsRepository.save(recommendationEntity);
        user.getRecommendations().add(recommendationEntity);
        usersRepository.save(user);
    }
}