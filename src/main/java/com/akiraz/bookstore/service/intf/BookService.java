package com.akiraz.bookstore.service.intf;

import java.util.List;

import com.akiraz.bookstore.model.entity.Book;

public interface BookService {

	public Book addBook(Book book);

	public Book removeBook(Long bookId);

	public Book updateBook(Long bookId, Book book);

	public List<Book> getAllBooks();
}
