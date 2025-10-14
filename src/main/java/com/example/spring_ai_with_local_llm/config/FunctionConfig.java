package com.example.spring_ai_with_local_llm.config;

import com.example.spring_ai_with_local_llm.function.DateTimeFunction;
import com.example.spring_ai_with_local_llm.tools.DateTimeTools;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.ai.tool.method.MethodToolCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
//import org.springframework.ai.tool.ToolRegistry;

@Configuration
public class FunctionConfig {

    @Bean
    public DateTimeFunction dateTimeFunction() {
        return new DateTimeFunction();
    }

    @Bean
    public ToolCallback dateTimeToolCallback(DateTimeTools tools) {
        Method method = ReflectionUtils.findMethod(DateTimeTools.class, "getCurrentDateTime");

        ToolDefinition toolDefinition = ToolDefinition.builder()
                .name("getCurrentDateTime")
                .description("Returns the current date and time based on the user's locale")
                .inputSchema("{\"type\": \"object\", \"properties\": {}, \"required\": []}")
                .build();

        return MethodToolCallback.builder()
                .toolDefinition(toolDefinition)
                .toolMethod(method)
                .toolObject(tools)
                .build();
    }



}

