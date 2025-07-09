package kh.edu.cstad.mbapi.service;

import kh.edu.cstad.mbapi.dto.CreateCustomerRequest;
import kh.edu.cstad.mbapi.dto.CustomerResponse;

public interface CustomerService {

    CustomerResponse createNew(CreateCustomerRequest createCustomerRequest);
}
