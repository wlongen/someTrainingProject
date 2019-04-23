package com.wenlong.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.wenlong.dao.UserDao;
import com.wenlong.entity.User;
import com.wenlong.utils.DButil;

public class UserDaoImpl implements UserDao {

	@Override
	public void add(User user) throws Exception {
		Connection conn = DButil.getConn();
		String sql = "insert into user (username,password,nickname,state,email,code) values(?,?,?,?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, user.getUsername());
		ps.setString(2, user.getPassword());
		ps.setString(3, user.getNickname());
		ps.setInt(4, user.getState());
		ps.setString(5, user.getEmail());
		ps.setString(6, user.getCode());
		ps.executeUpdate();
	}

	@Override
	public User findbyCode(String code) throws Exception {
		Connection connection = DButil.getConn();
		User user = new User();
		String sqlString = "select * from user where code=?";
		PreparedStatement preparedStatement = connection.prepareStatement(sqlString);
		preparedStatement.setString(1, code);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			user.setCode(code);
			user.setEmail(rs.getString("email"));
			user.setNickname(rs.getString("nickname"));
			user.setPassword(rs.getString("password"));
			user.setState(rs.getInt("state"));
			user.setUsername(rs.getString("username"));
		}
		return user;
	}

	@Override
	public void updateState(User user) throws Exception {
		Connection connection = DButil.getConn();

		String sql = "update user set state=? where username=?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		System.out.println(user);
		preparedStatement.setInt(1, user.getState());
		preparedStatement.setString(2, user.getUsername());
		int int1 = preparedStatement.executeUpdate();

		System.out.println(int1);
	}
}
