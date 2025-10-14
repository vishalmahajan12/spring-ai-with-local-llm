package com.example.spring_ai_with_local_llm.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Chat response containing the AI's reply")
public record ChatResponse(
    @Schema(description = "The AI's response message")
    String response
) {}




