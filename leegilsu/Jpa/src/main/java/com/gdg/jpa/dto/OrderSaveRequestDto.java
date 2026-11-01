package com.gdg.jpa.dto;

public record OrderSaveRequestDto(
        String phoneNumber, // 적립용 전화번호
        Integer waitingNumber, // 대기 순서
        String orderType, // 포장, 매장
        String menuName, // 메뉴명
        String temperature, // 아이스, 핫
        String sizeOption, // 레귤러, 메가
        String customOption, // 샷추가, 연하게 등
        Integer quantity, // 개수
        Integer totalPrice, // 총금액
        String paymentMethod // 결제수단
) {
}
