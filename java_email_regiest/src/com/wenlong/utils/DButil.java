package com.wenlong.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;


public class DButil {
	/**
	 * ��ȡ���ݿ�����
	 * @return
	 * @throws Exception
	 */
	 public static Connection getConn() throws Exception {
		 Properties properties = new Properties();
		  // ʹ��ClassLoader����properties�����ļ����ɶ�Ӧ��������
		 InputStream in = DButil.class.getClassLoader().getResourceAsStream("/db.properties");
		 // ʹ��properties�������������
		 properties.load(in);
		 //��ȡkey��Ӧ��valueֵ
		String url = properties.getProperty("url");
		String name = properties.getProperty("name");
		String pwd = properties.getProperty("pwd");
		String driver = properties.getProperty("driver");
		
		Class.forName(driver);
		//1.getConnection()����������MySQL���ݿ⣡��
		Connection conn = DriverManager.getConnection(url,name,pwd);
		return conn;
	 }
}
