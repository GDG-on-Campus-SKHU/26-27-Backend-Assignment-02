package com.gdg.jpa.service;

import com.gdg.jpa.domain.Customer;
import com.gdg.jpa.dto.CustomerInfoResponseDto;
import com.gdg.jpa.dto.CustomerSaveRequestDto;
import com.gdg.jpa.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public CustomerInfoResponseDto saveCustomer(CustomerSaveRequestDto customerSaveRequestDto) {
        Customer customer = Customer.builder()
                .phoneNumber(customerSaveRequestDto.phoneNumber())
                .build();

        customerRepository.save(customer);

        return CustomerInfoResponseDto.from(customer);
    }

    @Transactional(readOnly = true)
    public CustomerInfoResponseDto getCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 손님 정보를 찾을 수 없습니다."));

        return CustomerInfoResponseDto.from(customer);
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
        customerRepository.deleteById(customerId);
    }
}
