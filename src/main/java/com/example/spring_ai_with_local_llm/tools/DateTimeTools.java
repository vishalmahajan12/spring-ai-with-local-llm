package com.example.spring_ai_with_local_llm.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class DateTimeTools {

    @Tool(name = "getCurrentDateTime", description = "Returns the current date and time based on the user's locale")
    public String getCurrentDateTime() {
        return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
    }

}