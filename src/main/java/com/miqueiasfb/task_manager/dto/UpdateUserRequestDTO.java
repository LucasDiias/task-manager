package com.miqueiasfb.task_manager.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequestDTO(@NotBlank String name, @NotBlank @Email String email,
        String phone, LocalDate birthDate) {

}
