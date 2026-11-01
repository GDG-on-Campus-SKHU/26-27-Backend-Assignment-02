package com.gdg.jpa.controller;

import com.gdg.jpa.dto.CustomerInfoResponseDto;
import com.gdg.jpa.dto.CustomerSaveRequestDto;
import com.gdg.jpa.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerInfoResponseDto> saveCustomer(@RequestBody CustomerSaveRequestDto customerSaveRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.saveCustomer(customerSaveRequestDto));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerInfoResponseDto> getCustomer(@PathVariable Long customerId) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomer(customerId));
    }

    @GetMapping
    public ResponseEntity<List<CustomerInfoResponseDto>> getAllCustomer() {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getAllCustomer());
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}