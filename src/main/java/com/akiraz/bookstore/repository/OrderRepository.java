package com.akiraz.bookstore.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.akiraz.bookstore.model.entity.Order;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

	@Query(value = "from Order o where o.createdAt BETWEEN :startDate AND :endDate")
	List<Order> listOrdersBetweenStartdateAndEndDate(@Param("startDate") LocalDateTime startDate,
			@Param("endDate") LocalDateTime endDate);

	List<Order> findByCustomerId(Long customerId,Pageable pageable);
	
	List<Order> findAll();

}
