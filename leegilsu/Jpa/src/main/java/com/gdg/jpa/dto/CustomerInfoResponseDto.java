package com.gdg.jpa.dto;

import com.gdg.jpa.domain.Customer;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CustomerInfoResponseDto(
        Long id,
        String phoneNumber,
        int stampCount,
        int couponCount,
        LocalDateTime createdAt
) {

    public static CustomerInfoResponseDto from(Customer customer) {
        return CustomerInfoResponseDto.builder()
                .id(customer.getId())
                .phoneNumber(customer.getPhoneNumber())
                .stampCount(customer.getStampCount())
                .couponCount(customer.getCouponCount())
                .createdAt(customer.getCreatedAt())
                .build();
    }
}
