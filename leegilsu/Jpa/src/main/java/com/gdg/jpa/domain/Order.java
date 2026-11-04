package com.gdg.jpa.domain;

import com.gdg.jpa.domain.enums.CustomOption;
import com.gdg.jpa.domain.enums.OrderStatus;
import com.gdg.jpa.domain.enums.OrderType;
import com.gdg.jpa.domain.enums.PaymentMethod;
import com.gdg.jpa.domain.enums.SizeOption;
import com.gdg.jpa.domain.enums.Temperature;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer waitingNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderType orderType;

    @Column(nullable = false)
    private String menuName;

    @Enumerated(EnumType.STRING)
    private Temperature temperature;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SizeOption sizeOption;

    @Enumerated(EnumType.STRING)
    private CustomOption customOption;

    private int quantity;

    private int totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Builder
    public Order(Customer customer, Integer waitingNumber, OrderType orderType, String menuName,
                 Temperature temperature, SizeOption sizeOption, CustomOption customOption, int quantity,
                 int totalPrice, PaymentMethod paymentMethod) {
        this.customer = customer;
        this.waitingNumber = waitingNumber;
        this.orderType = orderType;
        this.menuName = menuName;
        this.temperature = temperature;
        this.sizeOption = sizeOption;
        this.customOption = (customOption != null) ? customOption : CustomOption.NONE;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.status = OrderStatus.PREPARING;
        this.createdAt = LocalDateTime.now();
    }

    public void update(OrderType orderType, String menuName, Temperature temperature,
                       SizeOption sizeOption, CustomOption customOption, int quantity,
                       int totalPrice, PaymentMethod paymentMethod, OrderStatus status) {
        this.orderType = orderType;
        this.menuName = menuName;
        this.temperature = temperature;
        this.sizeOption = sizeOption;
        this.customOption = customOption;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    // 제조 완료
    public void completeOrder() {
        if (this.status == OrderStatus.CANCELED) {
            throw new IllegalArgumentException("이미 취소된 주문은 완료 처리할 수 없습니다.");
        }
        if (this.status == OrderStatus.COMPLETED) {
            throw new IllegalArgumentException("이미 제조가 완료된 주문 입니다.");
        }
        this.status = OrderStatus.COMPLETED;
    }

    // 주문 취소
    public void cancelOrder() {
        if (this.status == OrderStatus.COMPLETED) {
            throw new IllegalArgumentException("이미 제조가 완료된 주문은 취소할 수 없습니다.");
        }
        if (this.status == OrderStatus.CANCELED) {
            throw new IllegalStateException("이미 취소 처리된 주문입니다.");
        }
        this.status = OrderStatus.CANCELED;
    }
}
