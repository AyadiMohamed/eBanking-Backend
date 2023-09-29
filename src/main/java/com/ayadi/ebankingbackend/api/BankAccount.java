package com.ayadi.ebankingbackend.api;

import com.ayadi.ebankingbackend.dtos.*;
import com.ayadi.ebankingbackend.exceptions.BalanceNotSufficientException;
import com.ayadi.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.ayadi.ebankingbackend.exceptions.CustomerNotFoundException;
import com.ayadi.ebankingbackend.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/eBank")
public class BankAccount {

    private BankAccountService bankAccountService;

    @GetMapping("/accounts")
    public List<BankAccountDTO> getAccounts(){
        return bankAccountService.getBankAccounts();
    }

    @GetMapping("/accounts/{id}")
    public BankAccountDTO getAccountsById(@PathVariable String id) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(id);
    }

    @PostMapping("/accounts/current")
    public CurrentAccountDTO saveCurrentAccount(@RequestBody CurrentAccountDTO currentAccountDTO) throws CustomerNotFoundException {
        return bankAccountService.saveCurrentBankAccount(currentAccountDTO.getBalance(), currentAccountDTO.getCustomerDTO().getId(), currentAccountDTO.getOverDraft());
    }

    @PostMapping("/accounts/saving")
    public SavingAccountDTO saveSavingAccount(@RequestBody SavingAccountDTO savingAccountDTO) throws CustomerNotFoundException {
        return bankAccountService.saveSavingBankAccount(savingAccountDTO.getBalance(), savingAccountDTO.getCustomerDTO().getId(), savingAccountDTO.getInterestRate());
    }

    @PutMapping("/accounts/current/{id}")
    public CurrentAccountDTO updateCurrentAccount(@RequestBody CurrentAccountDTO currentAccountDTO , @PathVariable String id){
        currentAccountDTO.setId(id);
        return bankAccountService.updateCurrentAccount(currentAccountDTO);
    }

    @PutMapping("/accounts/saving/{id}")
    public SavingAccountDTO updateSavingAccount(@RequestBody SavingAccountDTO savingAccountDTO, @PathVariable String id){
        savingAccountDTO.setId(id);
        return bankAccountService.updateSavingAccount(savingAccountDTO);
    }


    @DeleteMapping("/accounts/{id}")
    public void deleteAccount(@PathVariable String id){
        bankAccountService.deleteBankAccount(id);
    }

    @PostMapping("/accounts/credit")
    public void credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.credit(creditDTO.getAccountId(), creditDTO.getAmount(), creditDTO.getDescription() );
    }

    @PostMapping("/accounts/debit")
    public void debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.debit(debitDTO.getAccountId(), debitDTO.getAmount(), debitDTO.getDescription());
    }

    @PostMapping("/accounts/transferer")
    public void transferer(@RequestBody TransfertDTO transfertDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.transfer(transfertDTO.getAccountSource(), transfertDTO.getAccountDestination(), transfertDTO.getAmount());
    }

    @GetMapping("/accounts/{accountId}/opperations")
    public List<AccountOpertationDTO> getHistory(@PathVariable String accountId){
        return bankAccountService.accountHistory(accountId);
    }
}
