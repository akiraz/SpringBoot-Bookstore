
package com.akiraz.bookstore.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;

import com.akiraz.bookstore.exception.BookNotFoundException;
import com.akiraz.bookstore.exception.OutOfStockException;
import com.akiraz.bookstore.model.entity.Book;
import com.akiraz.bookstore.model.entity.Order;
import com.akiraz.bookstore.repository.BookRepository;
import com.akiraz.bookstore.repository.OrderRepository;
import com.akiraz.bookstore.service.intf.OrderService;
import com.akiraz.bookstore.sevice.OrderServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@RestClientTest(OrderService.class)
public class OrderServiceTest {

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private BookRepository bookRepository;

	@InjectMocks
	private OrderServiceImpl orderService;

	@Test
	public void testGetOrder() {
		// Create data
		Long orderId = 1L;
		Order order = buildOrder();

		// Create mock services behavior
		when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

		// Call test method
		Order returnedOrder = orderService.getOrder(orderId);

		// Generate expected datas
		Long expectedOrderId = order.getId();

		assertEquals(expectedOrderId, returnedOrder.getId());

		// verify the mock
		Mockito.verify(orderRepository).findById(orderId);
	}

	@Test(expected = OutOfStockException.class)
	public void testBuyBookOutOfStockException() {
		// Create data
		Order order = buildOrder();
		Book book = buildBook();

		// Create mock services behavior
		when(bookRepository.findById(order.getBookId())).thenReturn(Optional.of(book));

		// Call test method
		Order returnedOrder = orderService.buyBook(order);
	}
	
	@Test
	public void testBuyBook() {
		// Create data
		Order order = buildOrder();
		Book book = buildBookHaveStock();

		// Create mock services behavior
		when(bookRepository.findById(order.getBookId())).thenReturn(Optional.of(book));

		// Call test method
		Order returnedOrder = orderService.buyBook(order);
	}

	@Test(expected = BookNotFoundException.class)
	public void testBuyBookBookNotFoundException() {
		// Create data
		Order order = buildOrder();

		// Create mock services behavior
		when(bookRepository.findById(order.getBookId())).thenReturn(Optional.empty());

		// Call test method
		Order returnedOrder = orderService.buyBook(order);
	}

	private Order buildOrder() {
		return Order.builder().id(1L).customerId(100l).orderCount(5).bookId(10l).build();
	}
	
	private Book buildBook() {
		return Book.builder().id(3L).name("1984").description("Political fiction").stockCount(3).price(new BigDecimal(100)).build();
	}
	
	private Book buildBookHaveStock() {
		return Book.builder().id(3L).name("1984").description("Political fiction").stockCount(6).price(new BigDecimal(100)).build();
	}


}
