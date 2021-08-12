package com.akiraz.bookstore.sevice;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.akiraz.bookstore.exception.BookNotFoundException;
import com.akiraz.bookstore.model.entity.Book;
import com.akiraz.bookstore.repository.BookRepository;
import com.akiraz.bookstore.service.intf.BookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;

	public Book addBook(Book book) {
		Book savedBook = bookRepository.save(book);

		log.info("addBook()  Id: {} Name: {} Description: {}  Price: {}  StockCount: {}   added!", savedBook.getId(),
				savedBook.getName(), savedBook.getDescription(), savedBook.getPrice(),savedBook.getStockCount());

		return savedBook;
	}

	public Book removeBook(Long bookId) {
		Book book = null;
		book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
		bookRepository.delete(book);

		log.info("removeBook()  Id: {} removed!", bookId);

		return book;
	}

	public Book updateBook(Long bookId, Book book) {
		Book updatedBook = null;

		updatedBook = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);

		if (book.getStockCount() != null) {
			updatedBook.setStockCount(book.getStockCount());
		}
		
		if (book.getStockCount() != null) {
			updatedBook.setPrice(book.getPrice());
		}

		bookRepository.save(updatedBook);

		log.info("updateBook() Book Id: {} updated!", bookId);

		return updatedBook;
	}

	public List<Book> getAllBooks() {
		List<Book> bookList = null;
		bookList = bookRepository.findAll();
		if (!CollectionUtils.isEmpty(bookList)) {
			log.info("getAllBooks() Book listed! Count: {}", bookList.size());
			return bookList;
		}
		return Collections.emptyList();
	}

}
