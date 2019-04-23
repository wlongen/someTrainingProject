package com.wenlong.dao;

import com.wenlong.entity.User;

public interface UserDao {
	void add(User user) throws Exception;

	User findbyCode(String code) throws Exception;

	void updateState(User user) throws Exception;
}
