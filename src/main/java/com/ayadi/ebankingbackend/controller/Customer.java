package com.ayadi.ebankingbackend.controller;

import com.ayadi.ebankingbackend.dtos.CustomerDTO;

import com.ayadi.ebankingbackend.exceptions.CustomerNotFoundException;
import com.ayadi.ebankingbackend.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/eBank")
public class Customer {
    private CustomerService customerService;

    @GetMapping("/customers")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<CustomerDTO> customers(){
        return customerService.customerList();
    }

    @GetMapping("/customers/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public CustomerDTO getCustomerById(@PathVariable(name ="id") Long customerId) throws CustomerNotFoundException {
        return customerService.getCustomerById(customerId);
    }

    @PostMapping("/customers")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return customerService.saveCustomer(customerDTO);

    }

    @PutMapping("/customers/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO){
        customerDTO.setId(id);
        return customerService.updateCustomer(customerDTO);
    }

    @DeleteMapping("/customers/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteCustomer(@PathVariable Long id) throws CustomerNotFoundException {
        customerService.deleteCustomer(id);
    }
}
