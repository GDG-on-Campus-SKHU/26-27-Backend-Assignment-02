package com.gdg.jpa.dto;

import com.gdg.jpa.domain.enums.CustomOption;
import com.gdg.jpa.domain.enums.OrderStatus;
import com.gdg.jpa.domain.enums.OrderType;
import com.gdg.jpa.domain.enums.PaymentMethod;
import com.gdg.jpa.domain.enums.SizeOption;
import com.gdg.jpa.domain.enums.Temperature;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderUpdateRequestDto(
        @NotNull
        OrderType orderType,

        @NotBlank
        String menuName,

        @NotNull
        Temperature temperature,

        @NotNull
        SizeOption sizeOption,

        @NotNull
        CustomOption customOption,

        @NotNull
        @Positive(message = "개수는 0보다 커야 합니다.")
        Integer quantity,

        @NotNull
        @Positive(message = "금액은 0보다 커야 합니다.")
        Integer totalPrice,

        @NotNull
        PaymentMethod paymentMethod,

        @NotNull
        OrderStatus status
) {
}
