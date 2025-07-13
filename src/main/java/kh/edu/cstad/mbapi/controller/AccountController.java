package kh.edu.cstad.mbapi.controller;

import jakarta.validation.Valid;
import kh.edu.cstad.mbapi.dto.AccountResponse;
import kh.edu.cstad.mbapi.dto.CreateAccountRequest;
import kh.edu.cstad.mbapi.dto.UpdateAccountRequest;
import kh.edu.cstad.mbapi.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/accounts")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public List<AccountResponse> getAllAccounts(@RequestParam(value = "phone", defaultValue = "null") String phone){
        if(!phone.equals("null")){
            return accountService.findByCustomerPhone(phone);
        }
        return accountService.getAllAccounts();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{accountNumber}")
    public AccountResponse findByAccountNumber(@PathVariable("accountNumber") String accountNumber){
        return accountService.findByAccountNumber(accountNumber);
    }

//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping
//    public List<AccountResponse> findByCustomerPhoneNumber(@RequestParam(value = "phone", defaultValue = "null") String phone){
//        return accountService.findByCustomerPhone(phone);
//    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AccountResponse createAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest){
        return accountService.createNew(createAccountRequest);
    }

    @PatchMapping("/{accountNumber}")
    public AccountResponse updateByAccountNumber(@PathVariable String accountNumber,
                                                        @RequestBody UpdateAccountRequest updateAccountRequest){
        return accountService.updateByAccountNumber(accountNumber, updateAccountRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{accountNumber}")
    public void deleteByAccountNumber(@PathVariable String accountNumber){
        accountService.deleteByAccountNumber(accountNumber);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{accountNumber}/disable")
    public void disableByAccountNumber(@PathVariable String accountNumber){
        accountService.disableByAccountNumber(accountNumber);
    }
}
