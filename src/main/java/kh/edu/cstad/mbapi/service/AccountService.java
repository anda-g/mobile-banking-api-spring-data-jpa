package kh.edu.cstad.mbapi.service;

import kh.edu.cstad.mbapi.dto.AccountResponse;
import kh.edu.cstad.mbapi.dto.CreateAccountRequest;
import kh.edu.cstad.mbapi.dto.UpdateAccountRequest;

import java.util.List;

public interface AccountService {

    /*
    * 🧩 Create API endpoints to fulfill below functionalities: (7pts)
————————————
- Create a new account
- Find all accounts (1pt)
- Find an account by actNo (1pt)
- Find accounts by customer (1.5pts)
- Delete an account by actNo (1pt)
- Update an account information by actNo (1.5pts)
- Disable an account by actNo (update is_deleted to true) (1pt)
————————————
⭐️ NOTE:
* Define your own DTO correctly and validate all possible cases using standard message response and status code (3pts)
* Spring Data JPA with PostgreSQL (4pts)
* Spring Web MVC (CONTROLLER, SERVICE, REPOSITORY, DTO, DOMAIN MODEL) (4pts)
* Mapping DTO and Domain Model by using Lombok with MapStruct (2pts)
* */
    AccountResponse createNew(CreateAccountRequest createAccountRequest);
    List<AccountResponse> getAllAccounts();
    AccountResponse findByAccountNumber(String accountNumber);
    List<AccountResponse> findByCustomerPhone(String customerPhoneNumber);
    void deleteByAccountNumber(String accountNumber);
    AccountResponse updateByAccountNumber(String accountNumber, UpdateAccountRequest updateAccountRequest);
    void disableByAccountNumber(String accountNumber);

}
