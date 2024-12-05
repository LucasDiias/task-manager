package com.miqueiasfb.task_manager.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequestDTO(@NotBlank(message = "Refresh token is mandatory") String refreshToken) {

}
