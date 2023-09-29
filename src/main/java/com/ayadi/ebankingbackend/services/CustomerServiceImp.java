package com.ayadi.ebankingbackend.services;

import com.ayadi.ebankingbackend.dtos.CustomerDTO;
import com.ayadi.ebankingbackend.entities.Customer;
import com.ayadi.ebankingbackend.exceptions.CustomerNotFoundException;
import com.ayadi.ebankingbackend.mappers.CustomerMapper;
import com.ayadi.ebankingbackend.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CustomerServiceImp implements CustomerService {

    private CustomerRepository customerRepository;
    private CustomerMapper customerDtoMapper;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {

        Customer customer = customerDtoMapper.fromCustomerDto(customerDTO);
        Customer savedCustomer =customerRepository.save(customer);
        return customerDtoMapper.fromCustomer(savedCustomer);
    }
    @Override
    public List<CustomerDTO> customerList() {

        List<Customer> customerList = customerRepository.findAll();
        List<CustomerDTO> customerDTOS=customerList.stream().map(customer -> customerDtoMapper.fromCustomer(customer)).collect(Collectors.toList());
        return customerDTOS;

    }
    @Override
    public CustomerDTO getCustomerById(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer == null){
            throw new CustomerNotFoundException("Customer not found");
        }
        CustomerDTO customerDTO = customerDtoMapper.fromCustomer(customer);
        return customerDTO;
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO){
        Customer customer = customerDtoMapper.fromCustomerDto(customerDTO);
        Customer savedCustomer =customerRepository.save(customer);
        return customerDtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public void deleteCustomer(Long customerId) throws CustomerNotFoundException{
        customerRepository.deleteById(customerId);
    }

}
