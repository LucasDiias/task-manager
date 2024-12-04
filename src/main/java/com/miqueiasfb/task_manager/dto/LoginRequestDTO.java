package com.miqueiasfb.task_manager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record LoginRequestDTO(
    @NotNull @Email String email,
    @NotNull @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must have at least 8 characters, one uppercase letter, one lowercase letter, one number, and one special character") String password) {
}
