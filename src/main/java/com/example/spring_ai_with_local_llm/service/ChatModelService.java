package com.example.spring_ai_with_local_llm.service;

import com.example.spring_ai_with_local_llm.dto.ChatModelResponse;
import com.example.spring_ai_with_local_llm.function.DateTimeFunction;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
public class ChatModelService {

    private final ChatModel chatModel;

    public ChatModelService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public ChatModelResponse chat(String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        ChatResponse response = chatModel.call(prompt);
        
        String content = response.getResult().getOutput().getText();
        String model = response.getMetadata().getModel();
        Long tokens = response.getMetadata().getUsage() != null ? 
            response.getMetadata().getUsage().getTotalTokens() : 0L;
        
        return new ChatModelResponse(content, model, tokens);
    }

    public ChatModelResponse chatWithFunctions(String message) {
        // Note: Function calling with ChatModel directly requires more complex setup
        // For now, this will work the same as regular chat
        // To enable function calling with Ollama, you need models that support it (like mistral, llama3.1, etc.)
        DateTimeFunction function = new DateTimeFunction();
        DateTimeFunction.Response dateTimeResponse = function.apply(new DateTimeFunction.Request(null));
        
        String enhancedMessage = message + "\n\nCurrent date and time information: " + 
            dateTimeResponse.formattedDateTime() + " (" + dateTimeResponse.dayOfWeek() + ")";
        
        Prompt prompt = new Prompt(new UserMessage(enhancedMessage));
        ChatResponse response = chatModel.call(prompt);
        
        String content = response.getResult().getOutput().getText();
        String model = response.getMetadata().getModel();
        Long tokens = response.getMetadata().getUsage() != null ? 
            response.getMetadata().getUsage().getTotalTokens() : 0L;
        
        return new ChatModelResponse(content, model, tokens);
    }

    public String chatStream(String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        
        StringBuilder fullResponse = new StringBuilder();
        chatModel.stream(prompt)
            .doOnNext(response -> {
                if (response.getResult() != null && response.getResult().getOutput() != null) {
                    fullResponse.append(response.getResult().getOutput().getText());
                }
            })
            .blockLast();
        
        return fullResponse.toString();
    }
}

