package com.ayadi.ebankingbackend;

import com.ayadi.ebankingbackend.dtos.BankAccountDTO;
import com.ayadi.ebankingbackend.dtos.CurrentAccountDTO;
import com.ayadi.ebankingbackend.dtos.CustomerDTO;
import com.ayadi.ebankingbackend.dtos.SavingAccountDTO;
import com.ayadi.ebankingbackend.entities.*;
import com.ayadi.ebankingbackend.enums.AccountStatus;
import com.ayadi.ebankingbackend.enums.OperationType;
import com.ayadi.ebankingbackend.exceptions.BalanceNotSufficientException;
import com.ayadi.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.ayadi.ebankingbackend.exceptions.CustomerNotFoundException;
import com.ayadi.ebankingbackend.repositories.BankAccountRepository;
import com.ayadi.ebankingbackend.repositories.AccountOperationRepository;
import com.ayadi.ebankingbackend.repositories.CustomerRepository;
import com.ayadi.ebankingbackend.services.BankAccountService;
import com.ayadi.ebankingbackend.services.CustomerService;
import com.ayadi.ebankingbackend.services.UserInfoService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication

public class EbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner start(BankAccountService bankAccountService , CustomerService customerService , UserInfoService userInfoService){
        return args -> {

            UserInfo userInfo = new UserInfo();
            userInfo.setName("User");
            userInfo.setEmail("User@Gmail.com");
            userInfo.setPassword("12345");
            userInfo.setRoles("ROLE_USER");
            userInfoService.addUser(userInfo);

            UserInfo userInfo1 = new UserInfo();
            userInfo1.setName("Admin");
            userInfo1.setEmail("Admin@Gmail.com");
            userInfo1.setPassword("1234567");
            userInfo1.setRoles("ROLE_ADMIN");
            userInfoService.addUser(userInfo1);


            Stream.of("Client1","Client2","Client3").forEach(name->{
                CustomerDTO customer = new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                System.out.println(customer.getName());
                System.out.println(customer.getEmail());
                customerService.saveCustomer(customer);
            });
            customerService.customerList().forEach(cust->{
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*5000, cust.getId() , 500);
                    bankAccountService.saveSavingBankAccount(Math.random()*5000, cust.getId(), 4.5);
                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }


            });
            List<BankAccountDTO> bankAccountDTOS = bankAccountService.getBankAccounts();
            System.out.println("bank accounts"+bankAccountDTOS);
            for(BankAccountDTO acc : bankAccountDTOS ){
                System.out.println(acc);
                for(int i=0;i<10;i++){
                    String accountId ;
                    if(acc instanceof CurrentAccountDTO){
                        accountId = ((CurrentAccountDTO) acc).getId();
                    }else if(acc instanceof SavingAccountDTO){
                        accountId = ((SavingAccountDTO) acc).getId();
                    }
                    else {
                        throw new BankAccountNotFoundException("Account not found");
                    }
                    try {
                        bankAccountService.credit(accountId, 1000, "credit");
                        bankAccountService.debit(accountId, 1000,"debit");
                    } catch (BankAccountNotFoundException e) {
                        e.printStackTrace();
                    } catch (BalanceNotSufficientException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

}
