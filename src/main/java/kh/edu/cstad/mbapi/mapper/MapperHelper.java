package kh.edu.cstad.mbapi.mapper;

import kh.edu.cstad.mbapi.domain.AccountType;
import kh.edu.cstad.mbapi.domain.Customer;
import kh.edu.cstad.mbapi.repository.AccountTypeRepository;
import kh.edu.cstad.mbapi.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.mapstruct.Named;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@AllArgsConstructor
public class MapperHelper {

    private final CustomerRepository customerRepository;
    private final AccountTypeRepository accountTypeRepository;

    @Named("mapCustomer")
    public Customer mapCustomer(String customerPhoneNumber){
        return customerRepository.findByPhoneNumber(customerPhoneNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer not found"));
    }

    @Named("mapAccountType")
    public AccountType mapAccountType(Integer accountTypeId){
        return accountTypeRepository.findById(accountTypeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Account type not found"));
    }

    @Named("mapCustomerPhoneNumber")
    public String mapCustomerPhoneNumber(Customer customer){
        return customer.getPhoneNumber();
    }

    @Named("mapAccountTypeId")
    public Integer mapAccountTypeId(AccountType accountType){
        return accountType.getId();
    }
}
