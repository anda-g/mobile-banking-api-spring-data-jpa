package kh.edu.cstad.mbapi.service.impl;

import kh.edu.cstad.mbapi.domain.Account;
import kh.edu.cstad.mbapi.dto.AccountResponse;
import kh.edu.cstad.mbapi.dto.CreateAccountRequest;
import kh.edu.cstad.mbapi.dto.UpdateAccountRequest;
import kh.edu.cstad.mbapi.mapper.AccountMapper;
import kh.edu.cstad.mbapi.repository.AccountRepository;
import kh.edu.cstad.mbapi.service.AccountService;
import kh.edu.cstad.mbapi.util.AccountNumberGenerator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final AccountNumberGenerator accountNumberGenerator;

    @Override
    public AccountResponse createNew(CreateAccountRequest createAccountRequest) {

        HashMap<String, BigDecimal> currencyBalance = new HashMap<>();
        currencyBalance.put("USD", new BigDecimal(10));
        currencyBalance.put("KHR", new BigDecimal(40000));

        if(currencyBalance.get(createAccountRequest.accountCurrency()) == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Currency not found");
        }

        if(createAccountRequest.balance().compareTo(currencyBalance.get(createAccountRequest.accountCurrency())) < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds");
        }

        Account account = accountMapper.toAccount(createAccountRequest);
        account.setAccountNumber(accountNumberGenerator.generate());
        account.setIsDeleted(false);
        account.setSenderTransactions(new ArrayList<>());
        account.setReceiverTransactions(new ArrayList<>());
        return accountMapper.fromAccount(accountRepository.save(account));
    }

    @Override
    public List<AccountResponse> getAllAccounts() {

        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(accountMapper::fromAccount).toList();
    }

    @Override
    public AccountResponse findByAccountNumber(String accountNumber) {

        return accountMapper.fromAccount(
                accountRepository.findByAccountNumber(accountNumber)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"))
        );
    }

    @Override
    public List<AccountResponse> findByCustomerPhone(String customerPhoneNumber) {
        List<Account> accounts = accountRepository.findByCustomerPhoneNumber(customerPhoneNumber);
        return accounts.stream().map(accountMapper::fromAccount).toList();
    }

    @Override
    public void deleteByAccountNumber(String accountNumber) {
        accountRepository.delete(
                accountRepository.findByAccountNumber(accountNumber)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"))
        );
    }

    @Override
    public AccountResponse updateByAccountNumber(String accountNumber, UpdateAccountRequest updateAccountRequest) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        accountMapper.toAccountPartially(updateAccountRequest, account);
        return accountMapper.fromAccount(accountRepository.save(account));
    }

    @Override
    public void disableByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        account.setIsDeleted(true);
        accountRepository.save(account);
    }
}
