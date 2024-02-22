package com.hgame.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hgame.mapper.UserMapper;
import com.hgame.pojo.User;
import com.hgame.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public User loginService(User user) {
		User userM = userMapper.loginMapper(user.getUsername());
		if (userM != null && user.getPassword().equals(userM.getPassword())) {
			return userM;
		}
		return null;
	}

}
