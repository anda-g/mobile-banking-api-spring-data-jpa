package kh.edu.cstad.mbapi.controller;

import jakarta.validation.Valid;
import kh.edu.cstad.mbapi.dto.AccountTypeResponse;
import kh.edu.cstad.mbapi.dto.CreateAccountTypeRequest;
import kh.edu.cstad.mbapi.service.AccountTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/account-types")
public class AccountTypeController {

    private final AccountTypeService accountTypeService;

    @GetMapping
    public List<AccountTypeResponse> getAllAccountTypes() {
        return accountTypeService.getAllAccountTypes();
    }

    @GetMapping(path = "/{accountTypeId}")
    public AccountTypeResponse findById(@PathVariable Integer accountTypeId){
        return accountTypeService.findById(accountTypeId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AccountTypeResponse createNew(@Valid @RequestBody CreateAccountTypeRequest createAccountTypeRequest){
        return accountTypeService.createNew(createAccountTypeRequest);
    }
}
