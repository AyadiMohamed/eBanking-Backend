package com.ayadi.ebankingbackend.services;

import com.ayadi.ebankingbackend.dtos.AccountOpertationDTO;
import com.ayadi.ebankingbackend.dtos.BankAccountDTO;
import com.ayadi.ebankingbackend.dtos.CurrentAccountDTO;
import com.ayadi.ebankingbackend.dtos.SavingAccountDTO;
import com.ayadi.ebankingbackend.entities.*;
import com.ayadi.ebankingbackend.enums.AccountStatus;
import com.ayadi.ebankingbackend.enums.OperationType;
import com.ayadi.ebankingbackend.exceptions.BalanceNotSufficientException;
import com.ayadi.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.ayadi.ebankingbackend.exceptions.CustomerNotFoundException;
import com.ayadi.ebankingbackend.mappers.BankAccountMapper;
import com.ayadi.ebankingbackend.repositories.AccountOperationRepository;
import com.ayadi.ebankingbackend.repositories.BankAccountRepository;
import com.ayadi.ebankingbackend.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor

public class BankAcountServiceImp implements BankAccountService{

    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapper bankAccountMapper;

    @Override
    public CurrentAccountDTO saveCurrentBankAccount(double initialBalance, Long customerId, double overDraft) throws CustomerNotFoundException {
        CurrentAccount currentAccount = new CurrentAccount();
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer == null){
            throw new CustomerNotFoundException("Customer not found");
        }

        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreateAt(new Date());
        currentAccount.setStatus(AccountStatus.CREATED);
        currentAccount.setBalance(initialBalance);
        currentAccount.setCustomer(customer);
        currentAccount.setOverDraft(overDraft);
        CurrentAccount savedCurrentAccount=bankAccountRepository.save(currentAccount);



        return bankAccountMapper.fromCurrentAccount(savedCurrentAccount);
    }
    @Override
    public CurrentAccountDTO updateCurrentAccount(CurrentAccountDTO currentAccountDTO){
        CurrentAccount currentAccount = bankAccountMapper.fromCurrentAccountDTO(currentAccountDTO);
        CurrentAccount savedAccount = bankAccountRepository.save(currentAccount);
        return bankAccountMapper.fromCurrentAccount(savedAccount);

    }

    @Override
    public SavingAccountDTO saveSavingBankAccount(double initialBalance, Long customerId, double interestRate) throws CustomerNotFoundException {
        SavingAccount savingAccount = new SavingAccount();
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer == null)
            throw new CustomerNotFoundException("Customer not found");
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setBalance(initialBalance);
        savingAccount.setStatus(AccountStatus.CREATED);
        savingAccount.setCreateAt(new Date());
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);
        SavingAccount savedSavingAccount = bankAccountRepository.save(savingAccount);
        return bankAccountMapper.fromSavingAccount(savedSavingAccount);
    }
    @Override
    public SavingAccountDTO updateSavingAccount(SavingAccountDTO savingAccountDTO){
        SavingAccount savingAccount = bankAccountMapper.fromSavingAccountDTO(savingAccountDTO);
        SavingAccount savedAccount = bankAccountRepository.save(savingAccount);
        return bankAccountMapper.fromSavingAccount(savedAccount);
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount == null)
            throw new BankAccountNotFoundException("BankAccount not found");
        return bankAccountMapper.fromBankAccount(bankAccount);
    }
    @Override
    public List<BankAccountDTO> getBankAccounts(){
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        List<BankAccountDTO> bankAccountDTOS = bankAccounts.stream().map(bankAccount -> {
            if (bankAccount instanceof SavingAccount) {
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return bankAccountMapper.fromSavingAccount(savingAccount);
            } else {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return bankAccountMapper.fromCurrentAccount(currentAccount);
            }
        }).collect(Collectors.toList());
        return bankAccountDTOS;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        if(bankAccount.getBalance()<amount)
            throw new BalanceNotSufficientException("Balance not sufficient");
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setOperationType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOeprationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setOperationType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setOeprationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);


    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
        debit(accountIdSource, amount, "Transfert to"+accountIdDestination);
        credit(accountIdDestination,amount,"Transfer from"+accountIdSource);

    }
    @Override
    public void deleteBankAccount(String accountId){
        bankAccountRepository.deleteById(accountId);
    }

    @Override
    public List<AccountOpertationDTO> accountHistory(String accountId){
        List<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(accountId);
        return accountOperations.stream().map(op -> bankAccountMapper.fromAccountOperation(op)).collect(Collectors.toList());

    }

}
