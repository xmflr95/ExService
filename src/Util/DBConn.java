package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {
	private static Connection connection = null;
	
	private DBConn() {
		
	}
	
	public static Connection getConnection() {
		if (connection == null) {
			String className = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost/external?useSSL=true";
			String user = "root";
			String password = "root";
			
			try {
				Class.forName(className);
				connection = DriverManager.getConnection(url, user, password);
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}
	
	public static Connection getConnection(String className, String url, String user, String password) {
		if (connection == null) {			
			try {
				Class.forName(className);
				connection = DriverManager.getConnection(url, user, password);
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}
	
	public static void close() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			connection = null;
		}
	}
}
