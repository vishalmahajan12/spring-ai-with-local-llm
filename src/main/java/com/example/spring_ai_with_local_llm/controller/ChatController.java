package com.example.spring_ai_with_local_llm.controller;

import com.example.spring_ai_with_local_llm.dto.ChatRequest;
import com.example.spring_ai_with_local_llm.dto.ChatResponse;
import com.example.spring_ai_with_local_llm.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@Tag(name = "Chat", description = "Chat API endpoints for interacting with Ollama LLM")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    @Operation(
        summary = "Send a chat message",
        description = "Send a message to the AI chatbot and receive a response",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful response from AI",
                content = @Content(schema = @Schema(implementation = ChatResponse.class))
            )
        }
    )
    public ChatResponse chat(@RequestBody ChatRequest request) {
        String response = chatService.chat(request.message());
        return new ChatResponse(response);
    }

    @GetMapping
    @Operation(
        summary = "Send a chat message via GET",
        description = "Send a message to the AI chatbot using query parameters",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful response from AI",
                content = @Content(schema = @Schema(implementation = ChatResponse.class))
            )
        }
    )
    public ChatResponse chatGet(
        @Parameter(description = "The message to send to the AI", required = true)
        @RequestParam String message) {
        String response = chatService.chat(message);
        return new ChatResponse(response);
    }

    @PostMapping("/with-functions")
    @Operation(
        summary = "Send a chat message with function calling enabled",
        description = "Send a message to the AI chatbot with access to tools like getting current date/time",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful response from AI with function calling",
                content = @Content(schema = @Schema(implementation = ChatResponse.class))
            )
        }
    )
    public ChatResponse chatWithFunctions(@RequestBody ChatRequest request) {
        String response = chatService.chatWithFunctions(request.message());
        return new ChatResponse(response);
    }

    @GetMapping("/with-functions")
    @Operation(
        summary = "Send a chat message via GET with function calling",
        description = "Send a message with access to date/time functions using query parameters",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful response from AI with function calling",
                content = @Content(schema = @Schema(implementation = ChatResponse.class))
            )
        }
    )
    public ChatResponse chatWithFunctionsGet(
        @Parameter(description = "The message to send to the AI", required = true)
        @RequestParam String message) {
        String response = chatService.chatWithFunctions(message);
        return new ChatResponse(response);
    }
}
