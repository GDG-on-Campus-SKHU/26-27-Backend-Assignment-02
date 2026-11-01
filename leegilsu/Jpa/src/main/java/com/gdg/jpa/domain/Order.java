package com.gdg.jpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @Column(name = "waiting_number", nullable = false)
    private Integer waitingNumber;

    @Column(name = "order_type", nullable = false)
    private String orderType;

    @Column(name = "menu_name", nullable = false)
    private String menuName;

    private String temperature;

    @Column(name = "size_option")
    private String sizeOption;

    @Column(name = "custom_option")
    private String customOption;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Column(nullable = false)
    private String status;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Builder
    public Order(Customer customer, Integer waitingNumber, String orderType, String menuName,
                 String temperature, String sizeOption, String customOption, Integer quantity,
                 Integer totalPrice, String paymentMethod, String status) {
        this.customer = customer;
        this.waitingNumber = waitingNumber;
        this.orderType = (orderType != null) ? orderType : "TAKE_OUT";
        this.menuName = menuName;
        this.temperature = (temperature != null) ? temperature : "ICE";
        this.sizeOption = (sizeOption != null) ? sizeOption : "REGULAR";
        this.customOption = customOption;
        this.quantity = (quantity != null) ? quantity : 1;
        this.totalPrice = totalPrice;
        this.paymentMethod = (paymentMethod != null) ? paymentMethod : "CARD";
        this.status = (status != null) ? status : "PREPARING";
        this.createdAt = (createdAt != null) ? createdAt : LocalDateTime.now();
    }

    public void update(String orderType, String menuName, String temperature,
                       String sizeOption, String customOption, Integer quantity,
                       Integer totalPrice, String paymentMethod) {
        this.orderType = orderType;
        this.menuName = menuName;
        this.temperature = temperature;
        this.sizeOption = sizeOption;
        this.customOption = customOption;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
    }
}
