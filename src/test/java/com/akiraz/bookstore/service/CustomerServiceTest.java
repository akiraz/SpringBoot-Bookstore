package com.akiraz.bookstore.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintValidatorContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;

import com.akiraz.bookstore.exception.CustomerNotFoundException;
import com.akiraz.bookstore.exception.EmailExistException;
import com.akiraz.bookstore.model.entity.Customer;
import com.akiraz.bookstore.repository.CustomerRepository;
import com.akiraz.bookstore.service.intf.CustomerService;
import com.akiraz.bookstore.sevice.CustomerServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@RestClientTest(CustomerService.class)
public class CustomerServiceTest {

	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private CustomerServiceImpl customerService;

	@Mock
	private ConstraintValidatorContext constraintValidatorContext;

	

	@Test
	public void testAddCustomer() {
		// Create data
		Customer customer = buildCustomer();

		// Create mock services behavior
		when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.empty());
		when(customerRepository.save(customer)).thenReturn(customer);

		// Call test method
		Customer returnedCustomer = customerService.addCustomer(customer);

		// Generate expected datas
		String expectedName = customer.getName();
		String expectedSurname = customer.getSurname();
		String expectedAddress = customer.getAddress();

		// Check results
		assertNotNull(returnedCustomer);

		assertEquals(expectedName, returnedCustomer.getName());
		assertEquals(expectedSurname, returnedCustomer.getSurname());
		assertEquals(expectedAddress, returnedCustomer.getAddress());

		// verify the mock
		Mockito.verify(customerRepository).save(customer);
	}

	@Test
	public void testRemoveCustomer() {
		// Create data
		Customer customer = buildCustomer();

		// Create mock services behavior
		when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

		// Call test method
		Customer returnedCustomer = customerService.removeCustomer(customer.getId());

		// Generate expected datas

		Long expectedReturnId = customer.getId();

		// Check results
		assertEquals(expectedReturnId, returnedCustomer.getId());

		// verify the mock
		Mockito.verify(customerRepository).findById(customer.getId());
		Mockito.verify(customerRepository).delete(customer);
	}

	@Test(expected = CustomerNotFoundException.class)
	public void testRemoveCustomerCustomerNotFoundException() {
		// Create data
		Customer customer = buildCustomer();

		// Create mock services behavior
		when(customerRepository.findById(customer.getId())).thenReturn(Optional.empty());

		// Call test method
		Customer returnedCustomer = customerService.removeCustomer(customer.getId());
	}

	@Test(expected = EmailExistException.class)
	public void testAddCustomerEmailExistException() {
		// Create data
		Customer customer = buildCustomer();

		// Create mock services behavior
		when(customerRepository.findByEmail(customer.getEmail()).orElseThrow(EmailExistException::new));

		// Call test method
		Customer returnedCustomer = customerService.addCustomer(customer);
	}

	@Test
	public void testUpdateCustomer() {
		// Create data
		Customer customer = buildCustomer();

		// Create mock services behavior
		when(customerRepository.findById(3l)).thenReturn(Optional.of(customer));

		// Call test method
		Customer returnedCustomer = customerService.updateCustomer(3l, customer);

		// Check results
		assertEquals(returnedCustomer.getName(), customer.getName());
		assertEquals(returnedCustomer.getSurname(), customer.getSurname());
		assertEquals(returnedCustomer.getAddress(), customer.getAddress());

		// verify the mock
		Mockito.verify(customerRepository).findById(3l);
	}

	@Test(expected = CustomerNotFoundException.class)
	public void testUpdateCustomerCustomerNotFoundException() {
		// Create data
		Customer customer = buildCustomer();

		// Create mock services behavior
		when(customerRepository.findById(customer.getId())).thenReturn(Optional.empty());

		// Call test method
		Customer returnedCustomer = customerService.updateCustomer(customer.getId(), customer);
	}

	@Test
	public void testGetAllCustomers() {
		// Create data
		List<Customer> customerList = buildCustomerList();

		// Create mock services behavior
		when(customerRepository.findAll()).thenReturn(customerList);

		// Call test method
		List<Customer> returnedCustomerList = customerService.getAllCustomers();

		// Check results
		assertEquals(customerList.size(), returnedCustomerList.size());

		// verify the mock
		Mockito.verify(customerRepository).findAll();
	}

	private Customer buildCustomer() {
		return Customer.builder().id(1L).name("Alper").email("alper@alper.com").surname("Kiraz").address("Kadıköy")
				.build();
	}

	private List<Customer> buildCustomerList() {
		List<Customer> customerList = new ArrayList<Customer>();
		customerList.add(Customer.builder().id(1L).name("Alper").email("alper@alper.com").surname("Kiraz")
				.address("Kadıköy").build());
		customerList.add(Customer.builder().id(2L).name("Ahmet").email("ahmet@ahmet.com").surname("Kiraz")
				.address("İstanbul").build());
		return customerList;
	}
}
