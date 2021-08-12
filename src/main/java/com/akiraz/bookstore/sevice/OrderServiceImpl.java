package com.akiraz.bookstore.sevice;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akiraz.bookstore.exception.BookNotFoundException;
import com.akiraz.bookstore.exception.OrderCountNotValidException;
import com.akiraz.bookstore.exception.OrderNotFoundException;
import com.akiraz.bookstore.exception.OutOfStockException;
import com.akiraz.bookstore.model.entity.Book;
import com.akiraz.bookstore.model.entity.Order;
import com.akiraz.bookstore.repository.BookRepository;
import com.akiraz.bookstore.repository.OrderRepository;
import com.akiraz.bookstore.service.intf.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final BookRepository bookRepository;

	Lock lock = new ReentrantLock();

	@Override
	@Transactional
	public Order buyBook(Order order) {
		Book book = bookRepository.findById(order.getBookId()).orElseThrow(BookNotFoundException::new);
		try {
			lock.lock();
			
			if(order.getOrderCount() <= 0) {
				throw new OrderCountNotValidException();
			}
			
			if (book.getStockCount() >= order.getOrderCount()) {

				book.setStockCount(book.getStockCount() - order.getOrderCount());
				bookRepository.save(book);

				log.info("Book id: {} purchased! And its stock count decreased {}", book.getId(), book.getStockCount());

				orderRepository.save(order);

				return order;
			} else {
				throw new OutOfStockException();
			}
		} finally {
			lock.unlock();
		}

	}

	@Override
	public Order getOrder(Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
		log.info("getOrder()  order id: {}", orderId);
		return order;
	}

	@Override
	public List<Order> listOrders(LocalDateTime startDate, LocalDateTime endDate) {
		List<Order> orderList = orderRepository.listOrdersBetweenStartdateAndEndDate(startDate, endDate);
		log.info("Order count: {}", orderList.size());
		return orderList;
	}

}
