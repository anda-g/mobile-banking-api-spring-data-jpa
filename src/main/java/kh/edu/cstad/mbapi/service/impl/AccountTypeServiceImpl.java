package kh.edu.cstad.mbapi.service.impl;

import kh.edu.cstad.mbapi.domain.AccountType;
import kh.edu.cstad.mbapi.dto.AccountTypeResponse;
import kh.edu.cstad.mbapi.dto.CreateAccountTypeRequest;
import kh.edu.cstad.mbapi.mapper.AccountTypeMapper;
import kh.edu.cstad.mbapi.repository.AccountTypeRepository;
import kh.edu.cstad.mbapi.service.AccountTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountTypeServiceImpl implements AccountTypeService {

    private final AccountTypeRepository accountTypeRepository;
    private final AccountTypeMapper accountTypeMapper;

    @Override
    public AccountTypeResponse createNew(CreateAccountTypeRequest createAccountTypeRequest) {

        return accountTypeMapper.fromAccountType(
                accountTypeRepository.save(
                        accountTypeMapper.toAccountType(createAccountTypeRequest)
                )
        );
    }

    @Override
    public List<AccountTypeResponse> getAllAccountTypes() {
        List<AccountType> accountTypes = accountTypeRepository.findAll();
        return accountTypes.stream().map(accountTypeMapper::fromAccountType).toList();
    }

    @Override
    public AccountTypeResponse findById(Integer id) {
        return accountTypeMapper.fromAccountType(
                accountTypeRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account type not found"))
        );
    }
}
