package com.example.spring_ai_with_local_llm.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Chat response containing the AI's reply with metadata")
public record ChatModelResponse(
    @Schema(description = "The AI's response message")
    String response,
    @Schema(description = "The model used for generating the response")
    String model,
    @Schema(description = "Total tokens used in the request and response")
    Long totalTokens
) {}




