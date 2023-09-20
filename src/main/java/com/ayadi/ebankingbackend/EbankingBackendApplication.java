package com.ayadi.ebankingbackend;

import com.ayadi.ebankingbackend.entities.AccountOperation;
import com.ayadi.ebankingbackend.entities.CurrentAccount;
import com.ayadi.ebankingbackend.entities.Customer;
import com.ayadi.ebankingbackend.entities.SavingAccount;
import com.ayadi.ebankingbackend.enums.AccountStatus;
import com.ayadi.ebankingbackend.enums.OperationType;
import com.ayadi.ebankingbackend.repositories.BankAccountRepository;
import com.ayadi.ebankingbackend.repositories.AccountOperationRepository;
import com.ayadi.ebankingbackend.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, AccountOperationRepository accountOperationRepository){
        return args -> {
            Stream.of("Client1","Client2","Client3").forEach(name->{
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(cust->{
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*9000);
                currentAccount.setCreateAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer((cust));
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*9000);
                savingAccount.setCreateAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer((cust));
                savingAccount.setInterestRate(4.5);
                bankAccountRepository.save(savingAccount);


            });
            bankAccountRepository.findAll().forEach(acc->{
                for(int i=0;i<5;i++){
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setOeprationDate(new Date());
                    accountOperation.setAmount(Math.random()*500);
                    accountOperation.setOperationType(Math.random()>0.5? OperationType.DEBIT : OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);
                    accountOperationRepository.save(accountOperation);
                }
            });
        };
    }

}
