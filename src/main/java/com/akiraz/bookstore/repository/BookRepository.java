package com.akiraz.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akiraz.bookstore.model.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
