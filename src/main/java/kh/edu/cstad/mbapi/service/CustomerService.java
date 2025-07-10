package kh.edu.cstad.mbapi.service;

import kh.edu.cstad.mbapi.dto.CreateCustomerRequest;
import kh.edu.cstad.mbapi.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {

    /**
     * Create a new student and insert into the database
     * @author Anda
     * @param createCustomerRequest set of data for creating a customer
     * @return a customer that created
     */
    CustomerResponse createNew(CreateCustomerRequest createCustomerRequest);

    /**
     * Find all the customers in the database
     * @author Anda
     * @return a list of all customers in the database
     */
    List<CustomerResponse> findAllCustomers();

    /**
     * @author Anda
     * @param phoneNumber phone number of any customer for searching
     * @return a customer that the result by searching
     */
    CustomerResponse findByPhoneNumber(String phoneNumber);

}
