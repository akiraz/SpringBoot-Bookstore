package com.akiraz.bookstore.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akiraz.bookstore.model.entity.Book;
import com.akiraz.bookstore.service.intf.BookService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/book")
public class BookController {

	private final BookService bookService;

	@ApiOperation("addBook() - Adds book")
	@PostMapping("/")
	public ResponseEntity<Book> addBook(@Valid @RequestBody Book book) {
		return new ResponseEntity<>(bookService.addBook(book), HttpStatus.OK);
	}

	@ApiOperation("removeBook() - Removes the customer id")
	@DeleteMapping("/{bookId}")
	public ResponseEntity<Book> removeBook(@PathVariable(value = "bookId") Long bookId) {
		return new ResponseEntity<>(bookService.removeBook(bookId), HttpStatus.NO_CONTENT);
	}

	@ApiOperation("updateBook() - Updates book stocks")
	@PutMapping("/{bookId}")
	public ResponseEntity<Book> updateBook(@PathVariable(value = "bookId") Long bookId, @Valid @RequestBody Book book) {
		return new ResponseEntity<>(bookService.updateBook(bookId,book), HttpStatus.OK);
	}

	@ApiOperation("getAllBooks() - lists All books")
	@GetMapping("/")
	public ResponseEntity<List<Book>> getAllBooks() {
		return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
	}

}
