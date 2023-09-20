package com.ayadi.ebankingbackend.repositories;

import com.ayadi.ebankingbackend.entities.AccountOperation;
import com.ayadi.ebankingbackend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
}
