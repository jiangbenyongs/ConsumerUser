package com.xj.cloud.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xj.cloud.pojo.User;
import com.xj.cloud.service.impl.FeignClientFallbackFactory;

@FeignClient(name = "ProviderUser", fallbackFactory = FeignClientFallbackFactory.class)
public interface UserFeignClientAndHystrix {

	@RequestMapping(value = "/feignAndHystrix/user/{id}", method = RequestMethod.GET)
	public User findById(@PathVariable("id") Long id);
}
