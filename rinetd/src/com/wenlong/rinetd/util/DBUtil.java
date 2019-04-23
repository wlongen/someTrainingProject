package com.wenlong.rinetd.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * 连接数据库，创建连接
 * @author twl
 *
 */
public class DBUtil {
	private static Connection connection;
	
	
	public static Connection getConnection() {
		try {
			Properties properties = new Properties();
		    properties.load(DBUtil.class.getResourceAsStream("/db.properties"));
		    //获取key对应的value值
		    String driver = properties.getProperty("driver");
			String username = properties.getProperty("username");
			String password = properties.getProperty("password");
			String url = properties.getProperty("url");
			
			Class.forName(driver);
			connection = DriverManager.getConnection(url,username,password);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return connection;
	}
}
