package com.miqueiasfb.task_manager.dto;

import java.time.LocalDate;

public record TaskResponseDTO(
        Long id,
        String title,
        String description,
        int priority,
        boolean done,
        LocalDate doneAt,
        LocalDate createdAt) {

}
