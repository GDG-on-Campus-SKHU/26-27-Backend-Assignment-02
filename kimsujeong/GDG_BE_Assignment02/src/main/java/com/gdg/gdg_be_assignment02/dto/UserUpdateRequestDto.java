package com.gdg.gdg_be_assignment02.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserUpdateRequestDto(
        @NotBlank(message = "이름은 필수입니다.")
        String name,

        Integer age
) {
}
