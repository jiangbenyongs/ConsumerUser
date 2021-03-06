package com.xj.cloud.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.xj.cloud.pojo.User;
import com.xj.cloud.service.UserFeignClientAndHystrix;

import feign.hystrix.FallbackFactory;
@Component
public class FeignClientFallbackFactory implements FallbackFactory<UserFeignClientAndHystrix> {
	private static final Logger logger = LoggerFactory.getLogger(FeignClientFallbackFactory.class);

	@Override
	public UserFeignClientAndHystrix create(Throwable cause) {
		return new UserFeignClientAndHystrix() {
			@Override
			public User findById(Long id) {
				logger.info("fallback reason is:" + cause);
				User user = new User();
				if (cause instanceof IllegalArgumentException) {
					user.setId(-1L);
				} else {
					user.setId(-2L);
				}
				user.setName("defualt factoryname");
				return user;
			}
		};
	}



}
