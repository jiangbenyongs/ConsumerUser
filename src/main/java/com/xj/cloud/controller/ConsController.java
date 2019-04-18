package com.xj.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.xj.cloud.pojo.User;

@RestController
public class ConsController {

	@Autowired
	private RestTemplate res;

	@GetMapping("/user/{id}")
	public User findById(@PathVariable Long id) {

		return res.getForObject("http://localhost:8009/" + id, User.class);

	}
}
