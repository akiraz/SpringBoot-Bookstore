package com.akiraz.newsapp.response;

import java.util.List;

import com.akiraz.newsapp.model.entity.UsersNews;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ResponseAllUsersNews extends BaseResponse{
	
	List<UsersNews> usersNewList;
}
