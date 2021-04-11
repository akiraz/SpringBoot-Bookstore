package com.akiraz.newsapp.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_usersnews")
public class UsersNews {

	@Id
	@GeneratedValue
	private Long id;
	
	private Long userId;

	private Long newsID;

	private boolean marked;

}
