package com.example.spring_ai_with_local_llm.function;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

/**
 * Function to get current date and time information
 */
public class DateTimeFunction implements Function<DateTimeFunction.Request, DateTimeFunction.Response> {

    @Override
    public Response apply(Request request) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = LocalDate.now();
        
        String format = request.format() != null ? request.format() : "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        
        return new Response(
            now.format(formatter),
            today.toString(),
            now.toLocalTime().toString(),
            now.getDayOfWeek().toString(),
            now.getMonth().toString(),
            now.getYear()
        );
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonClassDescription("Request to get current date and time")
    public record Request(
        @JsonProperty(required = false)
        @JsonPropertyDescription("Date format pattern (e.g., 'yyyy-MM-dd HH:mm:ss'). Optional.")
        String format
    ) {}

    @JsonClassDescription("Current date and time information")
    public record Response(
        @JsonPropertyDescription("Formatted date and time")
        String formattedDateTime,
        @JsonPropertyDescription("Current date in ISO format (yyyy-MM-dd)")
        String date,
        @JsonPropertyDescription("Current time")
        String time,
        @JsonPropertyDescription("Day of the week")
        String dayOfWeek,
        @JsonPropertyDescription("Month name")
        String month,
        @JsonPropertyDescription("Current year")
        int year
    ) {}
}




