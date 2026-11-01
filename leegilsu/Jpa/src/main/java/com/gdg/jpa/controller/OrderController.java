package com.gdg.jpa.controller;

import com.gdg.jpa.dto.OrderInfoResponseDto;
import com.gdg.jpa.dto.OrderSaveRequestDto;
import com.gdg.jpa.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderInfoResponseDto> saveOrder(@RequestBody OrderSaveRequestDto orderSaveRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.saveOrder(orderSaveRequestDto));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderInfoResponseDto> getOrder(@PathVariable Long orderId) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrder(orderId));
    }

    @GetMapping
    public ResponseEntity<List<OrderInfoResponseDto>> getAllOrder() {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrder());
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<OrderInfoResponseDto> updateOrder(@PathVariable Long orderId,
                                                            @RequestBody OrderSaveRequestDto orderSaveRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.updateOrder(orderId, orderSaveRequestDto));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}