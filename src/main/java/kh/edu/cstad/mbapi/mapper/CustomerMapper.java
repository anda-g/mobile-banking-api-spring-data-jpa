package kh.edu.cstad.mbapi.mapper;

import kh.edu.cstad.mbapi.domain.Customer;
import kh.edu.cstad.mbapi.dto.CreateCustomerRequest;
import kh.edu.cstad.mbapi.dto.CustomerResponse;
import kh.edu.cstad.mbapi.dto.UpdateCustomerRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

//    === TELL EM WHAT YOU WANT TO MAP ===

    /**
     * Map from the Customer to CustomerResponse
     * @author Anda
     * @param customer a domain model for mapping
     * @return CustomerResponse DTO
     */
    CustomerResponse fromCustomer(Customer customer);

    /**
     * Map from CreateCustomerRequest to Customer domain model
     * @author Anda
     * @param createCustomerRequest a set of data for creating domain customer
     * @return domain model <code>Customer</code>
     */
    Customer toCustomer(CreateCustomerRequest createCustomerRequest);

    /**
     * This will convert from <code>UpdateCustomerRequest</code> to <code>Customer</code> and ignore the null value to keep the old info as customer in the database
     * @author Anda
     * @param updateCustomerRequest the source model info for updating
     * @param customer the target model
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toCustomerPartially(UpdateCustomerRequest updateCustomerRequest, @MappingTarget Customer customer);
}
