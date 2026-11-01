package com.gdg.jpa.dto;

import com.gdg.jpa.domain.Order;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record OrderInfoResponseDto(
        Long id,
        String customerPhoneNumber,
        Integer waitingNumber,
        String orderType,
        String menuName,
        String temperature,
        String sizeOption,
        String customOption,
        Integer quantity,
        Integer totalPrice,
        String paymentMethod,
        String status,
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
