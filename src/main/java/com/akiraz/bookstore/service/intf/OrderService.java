package com.akiraz.bookstore.service.intf;

import java.time.LocalDateTime;
import java.util.List;

import com.akiraz.bookstore.model.entity.Order;

public interface OrderService {

	public Order buyBook(Order book);

	public Order getOrder(Long orderId);

	public List<Order> listOrders(LocalDateTime startDate, LocalDateTime endDate);
}
