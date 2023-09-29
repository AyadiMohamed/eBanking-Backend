package com.ayadi.ebankingbackend.dtos;

import com.ayadi.ebankingbackend.enums.OperationType;
import lombok.Data;

import java.util.Date;

@Data
public class AccountOpertationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;
}
