package com.ayadi.ebankingbackend.services;

import com.ayadi.ebankingbackend.dtos.CustomerDTO;
import com.ayadi.ebankingbackend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {

    CustomerDTO saveCustomer(CustomerDTO customerDto);
    List<CustomerDTO> customerList();
    CustomerDTO getCustomerById(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId) throws CustomerNotFoundException;
}
