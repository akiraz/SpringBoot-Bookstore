package com.akiraz.bookstore.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akiraz.bookstore.model.entity.User;
import com.akiraz.bookstore.util.Constants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class UserController {

	@PostMapping("user")
	public User login(@RequestParam("user") String username, @RequestParam("password") String pwd) {

		String token = getJWTToken(username);
		User user = new User();
		user.setUser(username);
		user.setPwd(Constants.ASTERISK);
		user.setToken(token);
		return user;

	}

	private String getJWTToken(String username) {
		String secretKey = Constants.SECRET;
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(Constants.ROLE_USER);

		String token = Jwts.builder().setId(Constants.JWTTOKEN).setSubject(username)
				.claim(Constants.AUTHORITIES,
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + Constants.TOKEN_EXPIRATION))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

		return Constants.PREFIX + token;
	}
}