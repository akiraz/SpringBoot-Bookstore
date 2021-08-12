package com.akiraz.bookstore.service.intf;

import java.util.List;

import com.akiraz.bookstore.model.entity.Customer;
import com.akiraz.bookstore.model.entity.CustomerOrder;

public interface CustomerService {

	public Customer addCustomer(Customer customer);

	public Customer removeCustomer(Long customerId);

	public Customer updateCustomer(Long customerId, Customer customer);

	public List<Customer> getAllCustomers();
	
	public List<CustomerOrder> getAllCustomerOrders(Long customerId, int pageNo, int pageSize);
}
