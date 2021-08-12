package com.akiraz.bookstore.model.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_book")
public class Book {

    @Id
    @GeneratedValue
    private Long id;
    
    @NotEmpty(message = "name cannot be empty!")
    private String name;
    
    @NotEmpty(message = "description cannot be empty!")
    private String description;
    
    @NotNull
    private Integer stockCount;
    
    @NotNull
    private BigDecimal price;

}
