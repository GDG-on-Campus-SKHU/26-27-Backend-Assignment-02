package com.gdg.jpa.service;

import com.gdg.jpa.component.WaitingNumberGenerator;
import com.gdg.jpa.domain.Customer;
import com.gdg.jpa.domain.Order;
import com.gdg.jpa.dto.OrderInfoResponseDto;
import com.gdg.jpa.dto.OrderSaveRequestDto;
import com.gdg.jpa.dto.OrderUpdateRequestDto;
import com.gdg.jpa.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final WaitingNumberGenerator waitingNumberGenerator;

    @Transactional
    public OrderInfoResponseDto saveOrder(OrderSaveRequestDto orderSaveRequestDto) {

        Customer customer = customerService.getOrCreateCustomer(orderSaveRequestDto.phoneNumber());
        if (customer != null) {
            customer.addStamp(orderSaveRequestDto.quantity());
        }

        int waitingNumber = waitingNumberGenerator.getNextWaitingNumber();

        Order order = Order.builder()
                .customer(customer)
                .waitingNumber(waitingNumber)
                .orderType(orderSaveRequestDto.orderType())
                .menuName(orderSaveRequestDto.menuName())
                .temperature(orderSaveRequestDto.temperature())
                .sizeOption(orderSaveRequestDto.sizeOption())
                .customOption(orderSaveRequestDto.customOption())
                .quantity(orderSaveRequestDto.quantity())
                .totalPrice(orderSaveRequestDto.totalPrice())
                .paymentMethod(orderSaveRequestDto.paymentMethod())
                .build();

        orderRepository.save(order);

        return OrderInfoResponseDto.from(order);
    }

    @Transactional
    public OrderInfoResponseDto completeOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "요청하신 주문 정보를 찾을 수 없습니다."));

        try {
            order.completeOrder();
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return OrderInfoResponseDto.from(order);
    }

    @Transactional
    public OrderInfoResponseDto cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "요청하신 주문 정보를 찾을 수 없습니다."));

        try {
            order.cancelOrder();
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        if (order.getCustomer() != null) {
            order.getCustomer().subStamp(order.getQuantity());
        }

        return OrderInfoResponseDto.from(order);
    }

    @Transactional(readOnly = true)
    public OrderInfoResponseDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 주문 정보를 찾을 수 없습니다."));

        return OrderInfoResponseDto.from(order);
    }

    @Transactional(readOnly = true)
    public List<OrderInfoResponseDto> getAllOrder() {
        return orderRepository.findAllWithCustomer()
                .stream()
                .map(OrderInfoResponseDto::from)
                .toList();
    }

    @Transactional
    public OrderInfoResponseDto updateOrder(Long orderId, OrderUpdateRequestDto orderUpdateRequestDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 주문 정보를 찾을 수 없습니다."));

        if (order.getCustomer() != null && order.getQuantity() != orderUpdateRequestDto.quantity()) {
            int diff = orderUpdateRequestDto.quantity() - order.getQuantity(); // 수정된 수량 - 기존 수량

            if (diff > 0) {
                order.getCustomer().addStamp(diff);
            } else {
                order.getCustomer().subStamp(Math.abs(diff));
            }
        }

        order.update(
                orderUpdateRequestDto.orderType(),
                orderUpdateRequestDto.menuName(),
                orderUpdateRequestDto.temperature(),
                orderUpdateRequestDto.sizeOption(),
                orderUpdateRequestDto.customOption(),
                orderUpdateRequestDto.quantity(),
                orderUpdateRequestDto.totalPrice(),
                orderUpdateRequestDto.paymentMethod(),
                orderUpdateRequestDto.status()
        );

        return OrderInfoResponseDto.from(order);
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 주문 정보를 찾을 수 없습니다."));

        if (order.getCustomer() != null) {
            order.getCustomer().subStamp(order.getQuantity());
        }

        orderRepository.delete(order);
    }
}
