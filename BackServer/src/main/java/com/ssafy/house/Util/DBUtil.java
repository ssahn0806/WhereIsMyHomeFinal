package com.ssafy.house.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	
	private static final String DRIVER_CLASSNAME = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/housedb?serverTimezone=UTC";
	private static final String DB_USER = "ssafy";
	private static final String DB_PASS = "ssafy";
	
	static {
		
		try {
			// step1
			Class.forName(DRIVER_CLASSNAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
	}
	public static void close(AutoCloseable... resources) {
		for (AutoCloseable resource : resources) {
			if(resource != null) {
				try {
					resource.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} 
	}
}
