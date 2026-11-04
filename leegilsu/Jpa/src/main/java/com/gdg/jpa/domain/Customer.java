package com.gdg.jpa.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private Integer stampCount;

    @Column(nullable = false)
    private Integer couponCount;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @Builder
    public Customer(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.stampCount = 0;
        this.couponCount = 0;
        this.createdAt = LocalDateTime.now();
    }

    // 스탬프 적립, 쿠폰 전환
    public void addStamp(int count) {
        if (count <= 0) {
            return;
        }

        this.stampCount += count; // 주문 수량만큼 스탬프 추가

        // 스탬프 10개 시 쿠폰 1개로 전환
        if (this.stampCount >= 10) {
            this.couponCount += this.stampCount / 10;
            this.stampCount %= 10;
        }
    }

    // 스탬프 차감, 쿠폰 회수
    public void subStamp(int count) {
        if ( count <= 0) {
            return;
        }

        int totalStamp = this.couponCount * 10 + this.stampCount;
        totalStamp -= count; // 주문 취소 수량만큼 스탬프 차감
        if (totalStamp < 0) {
            totalStamp = 0;
        }

        this.couponCount = totalStamp / 10;
        this.stampCount = totalStamp % 10;
    }
}
