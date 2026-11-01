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

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "stamp_count", nullable = false)
    private Integer stampCount;

    @Column(name = "coupon_count", nullable = false)
    private Integer couponCount;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @Builder
    public Customer(String phoneNumber, Integer stampCount) {
        this.phoneNumber = phoneNumber;
        this.stampCount = (stampCount != null) ? stampCount : 0;
        this.couponCount = (couponCount != null) ? couponCount : 0;
        this.createdAt = (createdAt != null) ? createdAt : LocalDateTime.now();
    }

    // 스탬프 적립
    public void addStamp(Integer count) {
        if (count == null || count <= 0) {
            return;
        }
        if (this.stampCount == null) {
            this.stampCount = 0;
        }
        if (this.couponCount == null) {
            this.couponCount = 0;
        }
        this.stampCount += count; // 주문 수량만큼 스탬프 추가

        // 스탬프 10개 시 쿠폰 1개로 전환
        if (this.stampCount >= 10) {
            this.couponCount += this.stampCount / 10;
            this.stampCount %= 10;
        }
    }
}
