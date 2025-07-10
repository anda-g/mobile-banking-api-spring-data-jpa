package kh.edu.cstad.mbapi.service.impl;

import kh.edu.cstad.mbapi.domain.Customer;
import kh.edu.cstad.mbapi.dto.CreateCustomerRequest;
import kh.edu.cstad.mbapi.dto.CustomerResponse;
import kh.edu.cstad.mbapi.repository.CustomerRepository;
import kh.edu.cstad.mbapi.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponse createNew(CreateCustomerRequest createCustomerRequest) {

        if (customerRepository.existsByEmail(createCustomerRequest.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        } else if (customerRepository.existsByPhoneNumber(createCustomerRequest.phoneNumber())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number already exists");
        }

        log.info("Customer before save: {}", createCustomerRequest);


        Customer customer = new Customer();
        customer.setFullName(createCustomerRequest.fullName());
        customer.setGender(createCustomerRequest.gender());
        customer.setRemark(createCustomerRequest.remark());
        customer.setPhoneNumber(createCustomerRequest.phoneNumber());
        customer.setEmail(createCustomerRequest.email());
        customer.setIsDeleted(false);
        customer.setAccounts(new ArrayList<>());


        log.info("Customer before save: {}", customer);
        customer = customerRepository.save(customer);
        log.info("Customer after save: {}", customer.getId());

        return CustomerResponse.builder()
                .fullName(customer.getFullName())
                .gender(customer.getEmail())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .remark(customer.getRemark()).build();
    }

    @Override
    public List<CustomerResponse> findAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(
                c -> CustomerResponse.builder()
                        .fullName(c.getFullName())
                        .gender(c.getGender())
                        .email(c.getEmail())
                        .phoneNumber(c.getPhoneNumber())
                        .remark(c.getRemark()).build()
        ).toList();
    }
}
