package com.example.spring_ai_with_local_llm.service;

import com.example.spring_ai_with_local_llm.function.DateTimeFunction;
import com.example.spring_ai_with_local_llm.tools.DateTimeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.method.MethodToolCallback;
import org.springframework.ai.tool.support.ToolDefinitions;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

@Service
public class ChatService {

    private final ChatClient chatClient;
    private ToolCallback dateTimeToolCallback;
    String systemPromt = """
            You have access to a tool called `getCurrentDateTime`. It does not require any parameters.
            
            Use this tool to get the current date and time based on the user's locale
            """;

    public ChatService(ChatModel chatModel, ToolCallback dateTimeToolCallback) {
        this.chatClient = ChatClient.builder(chatModel).build();
        this.dateTimeToolCallback = dateTimeToolCallback;
    }

    public String chat(String message) {
        return chatClient.prompt()
                .system(systemPromt)
                .user(message)
                .toolCallbacks(dateTimeToolCallback)
                .call()
                .content();
    }



    public String chatWithFunctions(String message) {
        DateTimeFunction function = new DateTimeFunction();
        DateTimeFunction.Response dateTimeResponse = function.apply(new DateTimeFunction.Request(null));
        
        String systemPrompt = "You are a helpful AI assistant. You have access to the current date and time information: " +
            "Date: " + dateTimeResponse.date() + ", " +
            "Time: " + dateTimeResponse.time() + ", " +
            "Day: " + dateTimeResponse.dayOfWeek() + ", " +
            "Month: " + dateTimeResponse.month() + ", " +
            "Year: " + dateTimeResponse.year() + ". " +
            "Use this information to answer questions about the current date and time.";
        
        return chatClient.prompt()
                .system(systemPrompt)
                .user(message)
                .call()
                .content();
    }
}

