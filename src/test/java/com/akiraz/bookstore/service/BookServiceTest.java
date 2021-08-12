package com.akiraz.bookstore.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;

import com.akiraz.bookstore.exception.BookNotFoundException;
import com.akiraz.bookstore.model.entity.Book;
import com.akiraz.bookstore.repository.BookRepository;
import com.akiraz.bookstore.service.intf.BookService;
import com.akiraz.bookstore.sevice.BookServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@RestClientTest(BookService.class)
public class BookServiceTest {

	@Mock
	private BookRepository bookRepository;

	@InjectMocks
	private BookServiceImpl bookService;

	@Test
	public void testAddBook() {
		// Create data
		Book book = buildBook();

		// Create mock services behavior
		when(bookRepository.save(book)).thenReturn(book);

		// Call test method
		Book returnedBook = bookService.addBook(book);

		// Generate expected datas
		String expectedName = book.getName();
		BigDecimal expectedPrice = book.getPrice();
		Integer expectedStockCount = book.getStockCount();

		// Check results
		assertNotNull(returnedBook);

		assertEquals(expectedName, returnedBook.getName());
		assertEquals(expectedPrice, returnedBook.getPrice());
		assertEquals(expectedStockCount, returnedBook.getStockCount());

		// verify the mock
		Mockito.verify(bookRepository).save(book);
	}

	@Test
	public void testRemoveBook() {
		// Create data
		Book book = buildBook();

		// Create mock services behavior
		when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

		// Call test method
		Book returnedBook = bookService.removeBook(book.getId());

		// Generate expected datas

		Long expectedReturnId = book.getId();

		// Check results
		assertEquals(expectedReturnId, returnedBook.getId());

		// verify the mock
		Mockito.verify(bookRepository).findById(book.getId());
		Mockito.verify(bookRepository).delete(book);
	}

	@Test(expected = BookNotFoundException.class)
	public void testRemoveBookBookNotFoundException() {
		// Create data
		Book book = buildBook();

		// Create mock services behavior
		when(bookRepository.findById(book.getId())).thenReturn(Optional.empty());

		// Call test method
		Book returnedBook = bookService.removeBook(book.getId());
	}
	
	
	@Test
	public void testUpdateBook() {
		// Create data
		Book book = buildBook();

		// Create mock services behavior
		when(bookRepository.findById(3l)).thenReturn(Optional.of(book));

		// Call test method
		Book returnedBook = bookService.updateBook(3l, book);

		// Check results
		assertEquals(returnedBook.getPrice(), book.getPrice());
		assertEquals(returnedBook.getStockCount(), book.getStockCount());

		// verify the mock
		Mockito.verify(bookRepository).findById(3l);
	}

	@Test(expected = BookNotFoundException.class)
	public void testUpdateBookBookNotFoundException() {
		// Create data
		Book book = buildBook();

		// Create mock services behavior
		when(bookRepository.findById(book.getId())).thenReturn(Optional.empty());

		// Call test method
		Book returnedBook = bookService.updateBook(book.getId(), book);
	}

	@Test
	public void testGetAllBooks() {
		// Create data
		List<Book> bookList = buildBookList();

		// Create mock services behavior
		when(bookRepository.findAll()).thenReturn(bookList);

		// Call test method
		List<Book> returnedBookList = bookService.getAllBooks();

		// Check results
		assertEquals(bookList.size(), returnedBookList.size());

		// verify the mock
		Mockito.verify(bookRepository).findAll();
	}

	private Book buildBook() {
		return Book.builder().id(3L).name("1984").description("Political fiction").stockCount(3).price(new BigDecimal(100)).build();
	}

	private List<Book> buildBookList() {
		List<Book> bookList = new ArrayList<Book>();
		bookList.add( Book.builder().id(1L).name("1984").description("Political fiction").stockCount(3).price(new BigDecimal(100)).build());
		bookList.add( Book.builder().id(2L).name("Chess").description("Story").stockCount(1).price(new BigDecimal(100)).build());
		return bookList;
	}
}
