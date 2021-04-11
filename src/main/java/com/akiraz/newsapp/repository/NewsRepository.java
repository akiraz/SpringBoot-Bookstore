package com.akiraz.newsapp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.akiraz.newsapp.model.entity.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

	@Query(value = "from News n where n.createdAt BETWEEN :startTime AND :endTime and n.id in (:newsIdList) and (:category is null or  n.category = :category) and (:website is null or  n.website = :website)")
	List<News> findNewsFromThePastHour( @Param("startTime") Date startTime, @Param("endTime") Date endTime,@Param("newsIdList") List<Long> newsIdList,@Param("category") String category,@Param("website") String website);

}
