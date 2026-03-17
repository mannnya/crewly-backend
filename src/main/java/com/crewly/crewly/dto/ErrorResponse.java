package com.crewly.crewly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private Integer status;
    private String error;
    private String message;
    private LocalDateTime timestamp;
}