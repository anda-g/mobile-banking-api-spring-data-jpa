package kh.edu.cstad.mbapi.service;

import kh.edu.cstad.mbapi.dto.AccountTypeResponse;
import kh.edu.cstad.mbapi.dto.CreateAccountTypeRequest;

import java.util.List;

public interface AccountTypeService {

    AccountTypeResponse createNew(CreateAccountTypeRequest createAccountTypeRequest);
    List<AccountTypeResponse> getAllAccountTypes();
    AccountTypeResponse findById(Integer id);

}
