package com.gdg.jpa.service;

import com.gdg.jpa.domain.Customer;
import com.gdg.jpa.dto.CustomerInfoResponseDto;
import com.gdg.jpa.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public CustomerInfoResponseDto getCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 손님 정보를 찾을 수 없습니다."));

        return CustomerInfoResponseDto.from(customer);
    }

    @Transactional
    public Customer getOrCreateCustomer(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            return null;
        }

        return customerRepository.findByPhoneNumber(phoneNumber)
                .orElseGet(() -> customerRepository.save(
                        Customer.builder()
                                .phoneNumber(phoneNumber)
                                .build()
                ));
    }

    @Transactional(readOnly = true)
    public List<CustomerInfoResponseDto> getAllCustomer() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerInfoResponseDto::from)
                .toList();
    }

    @Transactional
    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                        .orElseThrow(() -> new IllegalArgumentException("요청하신 손님 정보를 찾을 수 없습니다."));

        customerRepository.delete(customer);
    }
}
