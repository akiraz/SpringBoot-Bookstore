package com.akiraz.newsapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.akiraz.newsapp.model.entity.UsersNews;

@Repository
public interface UserNewsRepository extends JpaRepository<UsersNews, Long> {
	
	 List<UsersNews> findUserNewsByUserId(@Param("userId") Long userId);
}
