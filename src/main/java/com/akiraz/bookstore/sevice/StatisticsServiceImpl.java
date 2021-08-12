package com.akiraz.bookstore.sevice;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.akiraz.bookstore.model.entity.Book;
import com.akiraz.bookstore.model.entity.Order;
import com.akiraz.bookstore.model.entity.Statistics;
import com.akiraz.bookstore.repository.BookRepository;
import com.akiraz.bookstore.repository.OrderRepository;
import com.akiraz.bookstore.service.intf.StatisticsService;
import com.akiraz.bookstore.util.DateUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class StatisticsServiceImpl implements StatisticsService {

	private final OrderRepository orderRepository;
	private final BookRepository bookRepository;

	@Override
	public List<Statistics> getStatistics() {
		List<Statistics> statisticsList = new ArrayList<>();
		try {
			List<Order> orderList = orderRepository.findAll();

			Map<String, List<Order>> result = orderList.stream().collect(Collectors.groupingBy(
					item -> DateUtil.localDateTimeToDateWithSlash((item.getCreatedAt()).withDayOfMonth(1))));

			if (!CollectionUtils.isEmpty(result)) {
				for (String key : result.keySet()) {
					int totalBookCount = 0;
					int orderCount = 0;
					BigDecimal totalPurchasedAmount = BigDecimal.ZERO;
					if (result.get(key) != null) {
						String monthName = null;
						for (int i = 0; i < result.get(key).size(); i++) {
							if (result.get(key).get(i) != null) {
								monthName = DateUtil.getMonthName(key);
								int orderedBookCount = result.get(key).get(i).getOrderCount();
								totalBookCount += orderedBookCount;
								Long orderedBookId = result.get(key).get(i).getBookId();
								totalPurchasedAmount = totalPurchasedAmount
										.add(getBookPurchasedAmount(orderedBookId, orderedBookCount));
								orderCount++;
							}
						}
						Statistics stats = new Statistics();
						stats.setTotalAmount(totalPurchasedAmount);
						stats.setTotalBookCount(totalBookCount);
						stats.setTotalOrderCount(orderCount);
						stats.setMonthName(monthName);
						statisticsList.add(stats);
					}
				}
			}
		} catch (ParseException e) {
			log.error("Parse Exception error!", e);
		}

		log.info("Statistics size: {}", statisticsList.size());
		return statisticsList;

	}

	private BigDecimal getBookPurchasedAmount(Long orderedBookId, int orderedBookCount) {
		Optional<Book> optBook = bookRepository.findById(orderedBookId);
		if (optBook.isPresent()) {
			Book book = optBook.get();
			return book.getPrice().multiply(new BigDecimal(orderedBookCount));
		}
		return BigDecimal.ZERO;

	}

}
