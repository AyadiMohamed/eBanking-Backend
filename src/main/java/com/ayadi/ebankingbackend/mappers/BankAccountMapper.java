package com.ayadi.ebankingbackend.mappers;

import com.ayadi.ebankingbackend.dtos.AccountOpertationDTO;
import com.ayadi.ebankingbackend.dtos.BankAccountDTO;
import com.ayadi.ebankingbackend.dtos.CurrentAccountDTO;
import com.ayadi.ebankingbackend.dtos.SavingAccountDTO;
import com.ayadi.ebankingbackend.entities.AccountOperation;
import com.ayadi.ebankingbackend.entities.BankAccount;
import com.ayadi.ebankingbackend.entities.CurrentAccount;
import com.ayadi.ebankingbackend.entities.SavingAccount;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BankAccountMapper {

    private CustomerMapper customerMapper;
    public CurrentAccountDTO fromCurrentAccount(CurrentAccount currentAccount){
        CurrentAccountDTO currentAccountDTO = new CurrentAccountDTO();
        BeanUtils.copyProperties(currentAccount,currentAccountDTO);
        currentAccountDTO.setCustomerDTO(customerMapper.fromCustomer(currentAccount.getCustomer()));
        currentAccountDTO.setType("Current Account");
        return currentAccountDTO;
    }

    public CurrentAccount fromCurrentAccountDTO(CurrentAccountDTO currentAccountDTO){
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentAccountDTO,currentAccount);
        currentAccount.setCustomer(customerMapper.fromCustomerDto(currentAccountDTO.getCustomerDTO()));
        return currentAccount;
    }

    public SavingAccountDTO fromSavingAccount(SavingAccount savingAccount){
        SavingAccountDTO savingAccountDTO = new SavingAccountDTO();
        BeanUtils.copyProperties(savingAccount,savingAccountDTO);
        savingAccountDTO.setCustomerDTO(customerMapper.fromCustomer(savingAccount.getCustomer()));
        savingAccountDTO.setType("Saving Account");
        return savingAccountDTO;
    }

    public SavingAccount fromSavingAccountDTO(SavingAccountDTO savingAccountDTO){
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(savingAccountDTO,savingAccount);
        savingAccount.setCustomer(customerMapper.fromCustomerDto(savingAccountDTO.getCustomerDTO()));
        return savingAccount;
    }

    public BankAccountDTO fromBankAccount(BankAccount bankAccount){
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        BeanUtils.copyProperties(bankAccount, bankAccountDTO);
        return bankAccountDTO;
    }

    public BankAccount fromBankAccountDTO(BankAccountDTO bankAccountDTO){
        BankAccount bankAccount = new BankAccount();
        BeanUtils.copyProperties(bankAccountDTO, bankAccount);
        return bankAccount;
    }

    public AccountOpertationDTO fromAccountOperation(AccountOperation accountOperation){
        AccountOpertationDTO accountOpertationDTO = new AccountOpertationDTO();
        BeanUtils.copyProperties(accountOperation,accountOpertationDTO);
        return accountOpertationDTO;
    }

    public AccountOperation fromAccountOperationDTO(AccountOpertationDTO accountOpertationDTO){
        AccountOperation accountOperation = new AccountOperation();
        BeanUtils.copyProperties(accountOpertationDTO, accountOperation);
        return accountOperation;
    }



}
