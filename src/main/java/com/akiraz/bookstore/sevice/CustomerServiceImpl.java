package com.akiraz.bookstore.sevice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.akiraz.bookstore.exception.CustomerNotFoundException;
import com.akiraz.bookstore.exception.EmailExistException;
import com.akiraz.bookstore.model.entity.Customer;
import com.akiraz.bookstore.model.entity.CustomerOrder;
import com.akiraz.bookstore.model.entity.Order;
import com.akiraz.bookstore.repository.CustomerRepository;
import com.akiraz.bookstore.repository.OrderRepository;
import com.akiraz.bookstore.service.intf.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	private final OrderRepository orderRepository;

	public Customer addCustomer(Customer customer) {

		customerRepository.findByEmail(customer.getEmail()).ifPresent(user1 -> {throw new EmailExistException();});

		Customer savedCustomer = customerRepository.save(customer);

		log.info("addCustomer()  Id: {} Name: {} Surname: {}  Address: {}  Email: {} added!", savedCustomer.getId(),
				savedCustomer.getName(), savedCustomer.getSurname(), savedCustomer.getAddress(),
				savedCustomer.getEmail());
		return savedCustomer;
	}

	public Customer removeCustomer(Long customerId) {
		Customer customer = null;
		customer = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
		customerRepository.delete(customer);

		log.info("removeCustomer()  Id: {} removed!", customerId);

		return customer;

	}

	public Customer updateCustomer(Long customerId, Customer customer) {
		Customer updatedCustomer = null;

		updatedCustomer = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);

		if (customer.getName() != null) {
			updatedCustomer.setName(customer.getName());
		}
		if (customer.getSurname() != null) {
			updatedCustomer.setSurname(customer.getSurname());
		}
		if (customer.getAddress() != null) {
			updatedCustomer.setAddress(customer.getAddress());
		}

		if (customer.getEmail() != null) {
			updatedCustomer.setEmail(customer.getEmail());
		}

		customerRepository.save(updatedCustomer);

		log.info("updateCustomer() Customer Id: {} updated!", customerId);

		return updatedCustomer;
	}

	public List<Customer> getAllCustomers() {
		List<Customer> customerList = null;
		customerList = customerRepository.findAll();
		if (!CollectionUtils.isEmpty(customerList)) {
			log.info("getAllCustomers() Customer listed! Count: {}", customerList.size());
			return customerList;
		}
		return Collections.emptyList();
	}

	@Override
	public List<CustomerOrder> getAllCustomerOrders(Long customerId, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		List<Order> orderList = orderRepository.findByCustomerId(customerId, pageable);
		Optional<Customer> optCustomer = customerRepository.findById(customerId);
		List<CustomerOrder> customerOrderList = new ArrayList<CustomerOrder>();

		if (optCustomer.isPresent() && !CollectionUtils.isEmpty(orderList)) {
			Customer customer = optCustomer.get();
			for (Order order : orderList) {
				CustomerOrder customerOrder = new CustomerOrder();
				customerOrder.setBookId(order.getBookId());
				customerOrder.setOrderCount(order.getOrderCount());
				customerOrder.setName(customer.getName());
				customerOrder.setSurname(customer.getSurname());
				customerOrder.setEmail(customer.getEmail());
				customerOrder.setAddress(customer.getAddress());
				customerOrderList.add(customerOrder);
			}

		}
		log.info("getAllCustomerOrders() Order listed! Count: {}", customerOrderList.size());
		return customerOrderList;
	}

}
