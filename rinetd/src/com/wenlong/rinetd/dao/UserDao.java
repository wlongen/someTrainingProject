package com.wenlong.rinetd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.wenlong.rinetd.entity.UserInDB;
import com.wenlong.rinetd.util.DBUtil;

public class UserDao {
	
	/**
	 * 添加用户
	 * @param user 要添加的用户信息
	 */
	public static void add(UserInDB user) {
		Connection connection = null;
		String sql = "insert into rinetd_user (username,pwd) values (?,?)";
		try {
			connection = DBUtil.getConnection();
			PreparedStatement pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, user.getUsername());
			pStatement.setString(2, user.getPwd());
			pStatement.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

		}
	}
	/**
	 * 删除用户
	 * @param username 要删除的用户的用户名
	 */
	public static void delete(String username) {
		Connection connection = null;
		try {
			connection = DBUtil.getConnection();
			String sql = "delete from rinetd_user where username=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 更改用户密码
	 * @param username 需要更改密码的用户
	 * @param newPwd 新的用户密码
	 */
	public static void update(String username, String newPwd) {
		Connection connection = null;
		try {
			connection = DBUtil.getConnection();
			String sql = "update rinetd_user set pwd=? where username=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, newPwd);
			preparedStatement.setString(2, username);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 查找全部用户信息
	 * @return
	 */
	public static List<UserInDB> findAll() {
		List<UserInDB> list = new ArrayList<UserInDB>();
		Connection connection = null;
		try {
			connection = DBUtil.getConnection();
			String sql = "select * from rinetd_user";
			PreparedStatement pStatement = connection.prepareStatement(sql);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				list.add(new UserInDB(resultSet.getString("username"), resultSet.getString("pwd")));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	/**
	 * 根据用户名查找用户信息
	 * @param username 需要查找的用户名
	 * @return
	 */
	public static UserInDB findByUsername(String username) {
		UserInDB userInDB = null;
		Connection connection = null;
		try {
			connection = DBUtil.getConnection();
			String sql = "select * from rinetd_user where username=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				userInDB = new UserInDB(resultSet.getString("username"), resultSet.getString("pwd"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
		return userInDB;
	}
}
