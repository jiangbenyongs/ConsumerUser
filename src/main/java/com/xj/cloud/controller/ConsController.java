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

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.xj.cloud.pojo.User;
import com.xj.cloud.service.UserFeignClient;
import com.xj.cloud.service.UserFeignClientAndHystrix;

@RestController
public class ConsController {
	private Logger log = LoggerFactory.getLogger(ConsController.class);

	@Autowired
	private RestTemplate res;
	@Autowired
	private LoadBalancerClient lbc;

	@Autowired
	private UserFeignClient ufc;
	
	@Autowired
	private UserFeignClientAndHystrix ufcah;
	
	/*
	 * 服务在注册中心注册  
	 * 直接通过注册名访问
	 */
	@GetMapping("/user/{id}")
	public User findById(@PathVariable Long id) {

		return res.getForObject("http://ProviderUser/" + id, User.class);

	}
	
	/*
	 * ribbion负载
	 * 判断选择的实例
	 */
	@GetMapping("/log-user-instance")
	public void logUserInstance() {
		ServiceInstance serviceIns = lbc.choose("ProviderUser");
		log.info("{}:{}:{}", serviceIns.getServiceId(), serviceIns.getHost(), serviceIns.getPort());
	}

	/*
	 * 使用feign
	 * hystrix整合feign
	 */
	@GetMapping("/feign/user/{id}")
	public User findByIdFeign(@PathVariable("id") Long id) {
		return ufc.findById(id);
	}

	/*
	 * use Hystrix
	 * spring cloud的容错机制
	 */
	@HystrixCommand(fallbackMethod = "findByIdHystrixFallback")
	@GetMapping("/hystrix/user/{id}")
	public User findByIdHystrix(@PathVariable("id") Long id) {
		
		return this.res.getForObject("http://ProviderUser/" + id, User.class);

	}
	
	/*
	 * fallbackMethod
	 * 该函数的入参必须跟调用该函数的入参一样
	 */
	public User findByIdHystrixFallback(Long id) {
		User user = new User();
		user.setId(-1L);
		user.setName("default");
		return user;
	}
	
	/*
	 * 容错
	 * feign整合hystrix factory
	 */
	@GetMapping("/feignAndHystrix/user/{id}")
	public User findByIdFeignAndHystrix(@PathVariable("id") Long id) {
		
		return ufcah.findById(id);
		
	}
}
