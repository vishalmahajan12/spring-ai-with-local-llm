package com.example.spring_ai_with_local_llm.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring AI with Ollama API")
                        .version("1.0.0")
                        .description("Chat API using Spring AI with local Ollama LLM")
                        .contact(new Contact()
                                .name("API Support")
                                .email("support@example.com")));
    }
}




