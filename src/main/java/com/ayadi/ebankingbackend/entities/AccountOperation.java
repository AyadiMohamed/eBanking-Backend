package com.ayadi.ebankingbackend.entities;

import com.ayadi.ebankingbackend.enums.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountOperation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date oeprationDate;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperationType operationType;
    private String description;
    @ManyToOne
    private BankAccount bankAccount;
}
