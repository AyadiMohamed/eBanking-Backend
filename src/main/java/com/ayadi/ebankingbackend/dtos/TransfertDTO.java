package com.ayadi.ebankingbackend.dtos;

import lombok.Data;

@Data
public class TransfertDTO {
    private String accountSource;
    private String accountDestination;
    private double amount;
    private String description;
}
