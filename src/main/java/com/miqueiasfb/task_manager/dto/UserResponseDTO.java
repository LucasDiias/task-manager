package com.miqueiasfb.task_manager.dto;

import java.time.LocalDate;

public record UserResponseDTO(String id, String name, String email, String phone, LocalDate birthDate) {

}
