package com.cwa.crudspringboot.controller;

import com.cwa.crudspringboot.entity.Customer;
import com.cwa.crudspringboot.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomer() {
        return new ResponseEntity<>(customerService.findAllCustomer(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Customer>createCustomer(@RequestBody Customer customer) {
        Customer customerCreated = customerService.saveOrUpdateCustomer(customer);
        return new ResponseEntity<>(customerCreated, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer=customerService.findCustomerById(id);

        return Objects.nonNull(customer) ? new ResponseEntity<>(customer, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customerDetail) {
        Customer existingCustomer = customerService.findCustomerById(id);

        if (Objects.nonNull(existingCustomer)) {
            existingCustomer.setFirstName(customerDetail.getFirstName());
            existingCustomer.setLastName(customerDetail.getLastName());
            existingCustomer.setEmail(customerDetail.getEmail());

            Customer updatedcustomer = customerService.saveOrUpdateCustomer(existingCustomer);
            return new ResponseEntity<>(updatedcustomer, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        Customer customer = customerService.findCustomerById(id);
        if (Objects.nonNull(customer)) {
            customerService.deleteCustomerById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
