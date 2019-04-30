package com.xj.cloud.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.xj.cloud.pojo.User;

@RestController
public class ConsController {
	private Logger log = LoggerFactory.getLogger(ConsController.class);
	
	@Autowired
	private RestTemplate res;
	@Autowired
	private LoadBalancerClient lbc;

	@GetMapping("/user/{id}")
	public User findById(@PathVariable Long id) {
		
		return res.getForObject("http://ProviderUser/" + id, User.class);

	}
	
	@GetMapping("/log-user-instance")
	public void logUserInstance() {
		ServiceInstance serviceIns = lbc.choose("ProviderUser");
		log.info("{}:{}:{}",serviceIns.getServiceId(),serviceIns.getHost(),serviceIns.getPort());
	}
}
