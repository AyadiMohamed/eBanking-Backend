package com.ayadi.ebankingbackend.services;

import com.ayadi.ebankingbackend.dtos.AccountOpertationDTO;
import com.ayadi.ebankingbackend.dtos.BankAccountDTO;
import com.ayadi.ebankingbackend.dtos.CurrentAccountDTO;
import com.ayadi.ebankingbackend.dtos.SavingAccountDTO;
import com.ayadi.ebankingbackend.exceptions.BalanceNotSufficientException;
import com.ayadi.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.ayadi.ebankingbackend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {

    CurrentAccountDTO saveCurrentBankAccount(double initialBalance, Long customerId, double overDraft) throws CustomerNotFoundException;

    CurrentAccountDTO updateCurrentAccount(CurrentAccountDTO currentAccountDTO);

    SavingAccountDTO saveSavingBankAccount(double initialBalance, Long customerId, double interestRate) throws CustomerNotFoundException;

    SavingAccountDTO updateSavingAccount(SavingAccountDTO savingAccountDTO);


    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;

    List<BankAccountDTO> getBankAccounts();

    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
     void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
     void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;

    void deleteBankAccount(String accountId);

    List<AccountOpertationDTO> accountHistory(String accountId);
}
