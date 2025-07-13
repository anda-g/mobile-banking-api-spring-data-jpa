package kh.edu.cstad.mbapi.mapper;

import kh.edu.cstad.mbapi.domain.AccountType;
import kh.edu.cstad.mbapi.dto.AccountTypeResponse;
import kh.edu.cstad.mbapi.dto.CreateAccountTypeRequest;
import kh.edu.cstad.mbapi.dto.UpdateAccountTypeRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AccountTypeMapper {


    AccountType toAccountType(CreateAccountTypeRequest createAccountTypeRequest);
    AccountTypeResponse fromAccountType(AccountType accountType);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toAccountTypePartially(UpdateAccountTypeRequest updateAccountTypeRequest,@MappingTarget AccountType accountType);
}
