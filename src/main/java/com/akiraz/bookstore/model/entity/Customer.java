package com.akiraz.bookstore.model.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_customer")
public class Customer
{
	
	
	@Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "Name cannot be empty!")
    private String name;
    
    @NotEmpty(message = "Surname cannot be empty!")
    private String surname;
    
    @NotEmpty(message = "Email cannot be empty!")
    @Email(message = "Email is wrong!")
    private String email;
    
    @NotEmpty(message = "Address cannot be empty!")
    private String address;
    
    private final Date createdAt = new Date();

}
