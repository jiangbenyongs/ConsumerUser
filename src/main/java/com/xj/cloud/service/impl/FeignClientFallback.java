package com.xj.cloud.service.impl;

import com.xj.cloud.pojo.User;
import com.xj.cloud.service.UserFeignClient;

public class FeignClientFallback implements UserFeignClient{

	@Override
	public User findById(Long id) {
		User user = new User();
		user.setId(1L);
		user.setName("default name");
		return user;
	}

}
