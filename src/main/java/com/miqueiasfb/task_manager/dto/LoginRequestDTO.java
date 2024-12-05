package com.miqueiasfb.task_manager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoginRequestDTO(
    @NotBlank(message = "E-mail is mandatory") @Email(message = "E-mail should be valid") String email,
    @NotBlank(message = "Password is mandatory") @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, and one number") String password) {
}
