package kh.edu.cstad.mbapi.mapper;

import kh.edu.cstad.mbapi.domain.Account;
import kh.edu.cstad.mbapi.dto.AccountResponse;
import kh.edu.cstad.mbapi.dto.CreateAccountRequest;
import kh.edu.cstad.mbapi.dto.UpdateAccountRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {MapperHelper.class})
public interface AccountMapper {

    @Mapping(source = "customerPhoneNumber", target = "customer", qualifiedByName = "mapCustomer")
    @Mapping(source = "accountTypeId", target = "accountType", qualifiedByName = "mapAccountType")
    @Mapping(source = "customerPhoneNumber", target = "overLimit", qualifiedByName = "mapOverLimit")
    Account toAccount(CreateAccountRequest createAccountRequest);

    @Mapping(source = "customer", target = "customerPhoneNumber", qualifiedByName = "mapCustomerPhoneNumber")
    @Mapping(source = "accountType", target = "accountTypeId", qualifiedByName = "mapAccountTypeId")
    AccountResponse fromAccount(Account account);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toAccountPartially(UpdateAccountRequest updateAccountRequest,@MappingTarget Account account);
}
