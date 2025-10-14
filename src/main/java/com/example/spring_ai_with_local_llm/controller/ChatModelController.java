package com.example.spring_ai_with_local_llm.controller;

import com.example.spring_ai_with_local_llm.dto.ChatModelResponse;
import com.example.spring_ai_with_local_llm.dto.ChatRequest;
import com.example.spring_ai_with_local_llm.dto.ChatResponse;
import com.example.spring_ai_with_local_llm.service.ChatModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat-model")
@Tag(name = "ChatModel", description = "Chat API endpoints using ChatModel directly with metadata")
public class ChatModelController {

    private final ChatModelService chatModelService;

    public ChatModelController(ChatModelService chatModelService) {
        this.chatModelService = chatModelService;
    }

    @PostMapping
    @Operation(
        summary = "Send a chat message using ChatModel",
        description = "Send a message to the AI chatbot using ChatModel directly and receive a detailed response",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful response from AI",
                content = @Content(schema = @Schema(implementation = ChatModelResponse.class))
            )
        }
    )
    public ChatModelResponse chat(@RequestBody ChatRequest request) {
        return chatModelService.chat(request.message());
    }

    @GetMapping
    @Operation(
        summary = "Send a chat message via GET using ChatModel",
        description = "Send a message to the AI chatbot using query parameters and ChatModel",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful response from AI",
                content = @Content(schema = @Schema(implementation = ChatModelResponse.class))
            )
        }
    )
    public ChatModelResponse chatGet(
        @Parameter(description = "The message to send to the AI", required = true)
        @RequestParam String message) {
        return chatModelService.chat(message);
    }

    @PostMapping("/stream")
    @Operation(
        summary = "Send a chat message with streaming response",
        description = "Send a message and receive a streaming response from the AI",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful streaming response from AI",
                content = @Content(schema = @Schema(implementation = ChatResponse.class))
            )
        }
    )
    public ChatResponse chatStream(@RequestBody ChatRequest request) {
        String response = chatModelService.chatStream(request.message());
        return new ChatResponse(response);
    }

    @PostMapping("/with-functions")
    @Operation(
        summary = "Send a chat message with function calling enabled",
        description = "Send a message to the AI chatbot with access to tools like getting current date/time and receive detailed metadata",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful response from AI with function calling and metadata",
                content = @Content(schema = @Schema(implementation = ChatModelResponse.class))
            )
        }
    )
    public ChatModelResponse chatWithFunctions(@RequestBody ChatRequest request) {
        return chatModelService.chatWithFunctions(request.message());
    }

    @GetMapping("/with-functions")
    @Operation(
        summary = "Send a chat message via GET with function calling",
        description = "Send a message with access to date/time functions and receive detailed metadata",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful response from AI with function calling and metadata",
                content = @Content(schema = @Schema(implementation = ChatModelResponse.class))
            )
        }
    )
    public ChatModelResponse chatWithFunctionsGet(
        @Parameter(description = "The message to send to the AI", required = true)
        @RequestParam String message) {
        return chatModelService.chatWithFunctions(message);
    }
}
