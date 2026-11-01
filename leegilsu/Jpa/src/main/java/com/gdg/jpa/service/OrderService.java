package com.gdg.jpa.service;

import com.gdg.jpa.domain.Customer;
import com.gdg.jpa.domain.Order;
import com.gdg.jpa.dto.OrderInfoResponseDto;
import com.gdg.jpa.dto.OrderSaveRequestDto;
import com.gdg.jpa.repository.CustomerRepository;
import com.gdg.jpa.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public OrderInfoResponseDto saveOrder(OrderSaveRequestDto orderSaveRequestDto) {
        Customer customer = null;

        if (orderSaveRequestDto.phoneNumber() != null && !orderSaveRequestDto.phoneNumber().isBlank()) {
            customer = customerRepository.findByPhoneNumber(orderSaveRequestDto.phoneNumber())
                    .orElseGet(() -> customerRepository.save(
                            Customer.builder()
                                    .phoneNumber(orderSaveRequestDto.phoneNumber())
                                    .build()
                    ));

            customer.addStamp(orderSaveRequestDto.quantity());
        }

        Order order = Order.builder()
                .customer(customer)
                .waitingNumber(orderSaveRequestDto.waitingNumber())
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

    @Transactional(readOnly = true)
    public OrderInfoResponseDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 주문 정보를 찾을 수 없습니다."));

        return OrderInfoResponseDto.from(order);
    }

    @Transactional(readOnly = true)
    public List<OrderInfoResponseDto> getAllOrder() {
        return orderRepository.findAll()
                .stream()
                .map(OrderInfoResponseDto::from)
                .toList();
    }

    @Transactional
    public OrderInfoResponseDto updateOrder(Long orderId, OrderSaveRequestDto orderSaveRequestDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 주문 정보를 찾을 수 없습니다."));

        order.update(
                orderSaveRequestDto.orderType(),
                orderSaveRequestDto.menuName(),
                orderSaveRequestDto.temperature(),
                orderSaveRequestDto.sizeOption(),
                orderSaveRequestDto.customOption(),
                orderSaveRequestDto.quantity(),
                orderSaveRequestDto.totalPrice(),
                orderSaveRequestDto.paymentMethod()
        );

        return OrderInfoResponseDto.from(order);
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
