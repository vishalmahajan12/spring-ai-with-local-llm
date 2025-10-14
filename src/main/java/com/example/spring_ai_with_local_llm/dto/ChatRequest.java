package com.example.spring_ai_with_local_llm.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Chat request containing the user message")
public record ChatRequest(
    @Schema(description = "The message to send to the AI", example = "What is Spring Boot?")
    String message
) {}




