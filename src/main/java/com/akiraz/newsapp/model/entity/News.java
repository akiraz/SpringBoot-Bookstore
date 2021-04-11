package com.akiraz.newsapp.model.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Table(name = "tbl_news")
public class News
{
	@Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "title cannot be empty!")
    private String title;
    
    @NotEmpty(message = "summaryText cannot be empty!")
    private String summaryText;
    
    @NotEmpty(message = "category cannot be empty!")
    private String category;
    
    @NotEmpty(message = "website cannot be empty!")
    private String website;
    
    @NotEmpty(message = "imageLink cannot be empty!")
    private String imageLink;

    private final Date createdAt = new Date();

}
