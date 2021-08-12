package com.akiraz.bookstore.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akiraz.bookstore.model.entity.Customer;
import com.akiraz.bookstore.model.entity.CustomerOrder;
import com.akiraz.bookstore.service.intf.CustomerService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customer")
public class CustomerController {

	private final CustomerService customerService;

	@ApiOperation("addCustomer() - Adds customer")
	@PostMapping("/")
	public ResponseEntity<Customer> addCustomer(@Valid @RequestBody Customer customer) {
		return new ResponseEntity<>(customerService.addCustomer(customer), HttpStatus.OK);
	}

	@ApiOperation("removeCustomer() - Removes the customer id")
	@DeleteMapping("/{customerId}")
	public ResponseEntity<Customer> removeCustomer(@PathVariable(value = "bookId") Long customerId) {
		return new ResponseEntity<>(customerService.removeCustomer(customerId), HttpStatus.NO_CONTENT);
	}

	@ApiOperation("updateCustomer() - Updates the given customer")
	@PutMapping("/{customerId}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "customerId") Long customerId,
			@Valid @RequestBody Customer customer) {
		return new ResponseEntity<>(customerService.updateCustomer(customerId, customer), HttpStatus.OK);
	}

	@ApiOperation("getAllCustomers() - lists All customers")
	@GetMapping("/")
	public ResponseEntity<List<Customer>> getAllCustomers() {
		return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
	}

	@ApiOperation("getAllCustomerOrders() - lists All customer orders")
	@GetMapping("/{customerId}/{pageNo}/{pageSize}")
	public ResponseEntity<List<CustomerOrder>> getAllCustomerOrders(@PathVariable(value = "customerId") Long customerId,
			@PathVariable(value = "pageNo") int pageNo, @PathVariable(value = "pageSize") int pageSize) {
		return new ResponseEntity<>(customerService.getAllCustomerOrders(customerId, pageNo, pageSize), HttpStatus.OK);
	}

}
