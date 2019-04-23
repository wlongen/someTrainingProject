package com.wenlong.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;


public class DButil {
	/**
	 * 获取数据库连接
	 * @return
	 * @throws Exception
	 */
	 public static Connection getConn() throws Exception {
		 Properties properties = new Properties();
		  // 使用ClassLoader加载properties配置文件生成对应的输入流
		 InputStream in = DButil.class.getClassLoader().getResourceAsStream("/db.properties");
		 // 使用properties对象加载输入流
		 properties.load(in);
		 //获取key对应的value值
		String url = properties.getProperty("url");
		String name = properties.getProperty("name");
		String pwd = properties.getProperty("pwd");
		String driver = properties.getProperty("driver");
		
		Class.forName(driver);
		//1.getConnection()方法，连接MySQL数据库！！
		Connection conn = DriverManager.getConnection(url,name,pwd);
		return conn;
	 }
}
