package com.gdg.jpa.dto;

import com.gdg.jpa.domain.enums.CustomOption;
import com.gdg.jpa.domain.enums.OrderType;
import com.gdg.jpa.domain.enums.PaymentMethod;
import com.gdg.jpa.domain.enums.SizeOption;
import com.gdg.jpa.domain.enums.Temperature;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record OrderSaveRequestDto(
        @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호는 010-0000-0000 형식으로 입력해주세요.")
        String phoneNumber, // 적립용 전화번호

        @NotNull
        OrderType orderType, // 포장, 매장

        @NotBlank
        String menuName, // 메뉴명

        @NotNull
        Temperature temperature, // 아이스, 핫

        @NotNull
        SizeOption sizeOption, // 스몰, 레귤러, 라지

        @NotNull
        CustomOption customOption, // 샷추가, 연하게 등

        @NotNull
        @Positive(message = "개수는 0보다 커야 합니다.")
        int quantity, // 개수

        @NotNull
        @Positive(message = "금액은 0보다 커야 합니다.")
        int totalPrice, // 총금액

        @NotNull
        PaymentMethod paymentMethod // 결제수단
) {
        public OrderSaveRequestDto {
                if (phoneNumber != null && phoneNumber.isBlank()) {
                        phoneNumber = null;
                }
        }
}
