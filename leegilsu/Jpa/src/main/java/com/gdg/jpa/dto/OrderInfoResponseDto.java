package com.gdg.jpa.dto;

import com.gdg.jpa.domain.Order;
import com.gdg.jpa.domain.enums.CustomOption;
import com.gdg.jpa.domain.enums.OrderStatus;
import com.gdg.jpa.domain.enums.OrderType;
import com.gdg.jpa.domain.enums.PaymentMethod;
import com.gdg.jpa.domain.enums.SizeOption;
import com.gdg.jpa.domain.enums.Temperature;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record OrderInfoResponseDto(
        Long id,
        String customerPhoneNumber,
        Integer waitingNumber,
        OrderType orderType,
        String menuName,
        Temperature temperature,
        SizeOption sizeOption,
        CustomOption customOption,
        int quantity,
        int totalPrice,
        PaymentMethod paymentMethod,
        OrderStatus status,
        LocalDateTime createdAt
) {
    public static OrderInfoResponseDto from(Order order) {
        return OrderInfoResponseDto.builder()
                .id(order.getId())
                .customerPhoneNumber(order.getCustomer() != null ? order.getCustomer().getPhoneNumber() : null)
                .waitingNumber(order.getWaitingNumber())
                .orderType(order.getOrderType())
                .menuName(order.getMenuName())
                .temperature(order.getTemperature())
                .sizeOption(order.getSizeOption())
                .customOption(order.getCustomOption())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .paymentMethod(order.getPaymentMethod())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
