package com.ayadi.ebankingbackend.dtos;

import com.ayadi.ebankingbackend.entities.AccountOperation;
import com.ayadi.ebankingbackend.entities.Customer;
import com.ayadi.ebankingbackend.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data

public class CurrentAccountDTO extends BankAccountDTO {


    private String id;
    private double balance;
    private Date createAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double overDraft;

}
