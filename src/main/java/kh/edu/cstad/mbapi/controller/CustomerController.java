package kh.edu.cstad.mbapi.controller;


import jakarta.validation.Valid;
import kh.edu.cstad.mbapi.dto.CreateCustomerRequest;
import kh.edu.cstad.mbapi.dto.CustomerResponse;
import kh.edu.cstad.mbapi.dto.UpdateCustomerRequest;
import kh.edu.cstad.mbapi.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<CustomerResponse> getAllCustomers() {
        return customerService.findAllCustomers();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CustomerResponse createCustomer(@Valid @RequestBody CreateCustomerRequest createCustomerRequest) {
        return customerService.createNew(createCustomerRequest);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/{phoneNumber}")
    public CustomerResponse findByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        return customerService.findByPhoneNumber(phoneNumber);
    }

    @PatchMapping("/{phoneNumber}")
    public CustomerResponse updateCustomer(@PathVariable String phoneNumber,@RequestBody UpdateCustomerRequest updateCustomerRequest) {
        return customerService.updateByPhoneNumber(phoneNumber, updateCustomerRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{phoneNumber}")
    public void deleteByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        customerService.deleteByPhoneNumber(phoneNumber);
    }
}
