package kh.edu.cstad.mbapi.service.impl;

import jakarta.transaction.Transactional;
import kh.edu.cstad.mbapi.domain.Customer;
import kh.edu.cstad.mbapi.domain.CustomerSegment;
import kh.edu.cstad.mbapi.domain.KYC;
import kh.edu.cstad.mbapi.dto.CreateCustomerRequest;
import kh.edu.cstad.mbapi.dto.CustomerResponse;
import kh.edu.cstad.mbapi.dto.UpdateCustomerRequest;
import kh.edu.cstad.mbapi.mapper.CustomerMapper;
import kh.edu.cstad.mbapi.repository.CustomerRepository;
import kh.edu.cstad.mbapi.repository.CustomerSegmentRepository;
import kh.edu.cstad.mbapi.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CustomerSegmentRepository customerSegmentRepository;

    @Override
    public CustomerResponse createNew(CreateCustomerRequest createCustomerRequest) {
        if (customerRepository.existsByEmail(createCustomerRequest.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        } else if (customerRepository.existsByPhoneNumber(createCustomerRequest.phoneNumber())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number already exists");
        }

        KYC kyc = new KYC();
        kyc.setIsDeleted(false);
        kyc.setIsVerify(false);
        kyc.setNationalCardId(createCustomerRequest.nationalCardId());

        Customer customer = customerMapper.toCustomer(createCustomerRequest);
        customer.setIsDeleted(false);
        customer.setAccounts(new ArrayList<>());
        customer.setKyc(kyc);
        CustomerSegment segment = customerSegmentRepository.findById(createCustomerRequest.segmentId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Segment not found")
        );
        customer.setCustomerSegment(segment);
        kyc.setCustomer(customer);
        return customerMapper.fromCustomer(customerRepository.save(customer));
    }

    @Override
    public List<CustomerResponse> findAllCustomers() {
        List<Customer> customers = customerRepository.findByIsDeletedFalse();
        return customers.stream().map(customerMapper::fromCustomer).toList();
    }

    @Override
    public CustomerResponse findByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumberAndIsDeletedFalse(phoneNumber)
                .map(customerMapper::fromCustomer)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Phone number not found"));
    }

    @Override
    public CustomerResponse updateByPhoneNumber(String phoneNumber, UpdateCustomerRequest updateCustomerRequest) {
        Customer customer = customerRepository.findByPhoneNumberAndIsDeletedFalse(phoneNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Phone number not found"));
        customerMapper.toCustomerPartially(updateCustomerRequest, customer);
        return customerMapper.fromCustomer(customerRepository.save(customer));

    }

    @Override
    public void deleteByPhoneNumber(String phoneNumber) {
        Customer customer = customerRepository.findByPhoneNumberAndIsDeletedFalse(phoneNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Phone number not found"));
        customerRepository.delete(customer);
    }

    @Transactional
    @Override
    public void disableByPhoneNumber(String phoneNumber) {
        if(!customerRepository.isExistsByPhoneNumber(phoneNumber)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Phone number not found");
        }
        customerRepository.disableByPhoneNumber(phoneNumber);
    }

    @Override
    public void verifyKycByPhoneNumber(String phoneNumber) {
        Customer customer = customerRepository.findByPhoneNumberAndIsDeletedFalse(phoneNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Phone number not found"));
        customer.getKyc().setIsVerify(true);
        customerRepository.save(customer);
    }
}
